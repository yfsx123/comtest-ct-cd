package comtest.ct.cd.yoasfebrianus.view.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import comtest.ct.cd.yoasfebrianus.di.UserDispatcherProvider
import comtest.ct.cd.yoasfebrianus.domain.UserUseCase
import comtest.ct.cd.yoasfebrianus.util.viewmodel.BaseViewModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserListModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val baseDispatcher: UserDispatcherProvider,
    private val userUseCase: UserUseCase)
    : BaseViewModel(baseDispatcher.ui()) {

    var page = 1

    val userDataResp: LiveData<Result<UserListModel>>
        get() = _userDataResp
    private val _userDataResp = MutableLiveData<Result<UserListModel>>()


    val userDataMoreResp: LiveData<Result<UserListModel>>
        get() = _userDataMoreResp
    private val _userDataMoreResp = MutableLiveData<Result<UserListModel>>()

    fun getUserData(keyword: String) {
        page = 1
        viewModelScope.launch(baseDispatcher.io()) {
            try {
                userUseCase.setParams(keyword, page)
                val data = userUseCase.executeOnBackground()
                if (data.dataList.isNotEmpty()) {
                    _userDataResp.postValue(Result.success(data))
                } else {
                    _userDataResp.postValue(Result.failure(Exception("empty data")))
                }
            } catch (e: Exception) {
                _userDataResp.postValue(Result.failure(e))
            }
        }
    }

    fun getUserDataMore(keyword: String) {
        page = page + 1
        viewModelScope.launch(baseDispatcher.io()) {
            try {
                userUseCase.setParams(keyword, page)
                val data = userUseCase.executeOnBackground()
                _userDataMoreResp.postValue(Result.success(data))
            } catch (e: Exception) {
                _userDataMoreResp.postValue(Result.failure(e))
            }
        }
    }

}