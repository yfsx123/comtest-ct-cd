package comtest.ct.cd.yoasfebrianus.view.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import comtest.ct.cd.yoasfebrianus.data.datasource.UserRemoteDataSource
import comtest.ct.cd.yoasfebrianus.data.pojo.UserPojo
import comtest.ct.cd.yoasfebrianus.di.UserDispatcherProvider
import comtest.ct.cd.yoasfebrianus.domain.UserUseCase
import comtest.ct.cd.yoasfebrianus.util.viewmodel.BaseViewModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserListModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val baseDispatcher: UserDispatcherProvider,
    private val userUseCase: UserUseCase)
    : BaseViewModel(baseDispatcher.ui()){

    val userDataResp: LiveData<Result<UserListModel>>
        get() = _userDataResp
    private val _userDataResp = MutableLiveData<Result<UserListModel>>()


    val userDataMoreResp: LiveData<Result<UserListModel>>
        get() = _userDataMoreResp
    private val _userDataMoreResp = MutableLiveData<Result<UserListModel>>()

    fun getUserData(keyword: String) {
        viewModelScope.launch(baseDispatcher.io()) {
            try {
                userUseCase.setParams(keyword)
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
        viewModelScope.launch(baseDispatcher.io()) {
            try {
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

}