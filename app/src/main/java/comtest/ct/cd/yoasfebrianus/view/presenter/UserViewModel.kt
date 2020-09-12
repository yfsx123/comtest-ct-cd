package comtest.ct.cd.yoasfebrianus.view.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import comtest.ct.cd.yoasfebrianus.data.datasource.UserRemoteDataSource
import comtest.ct.cd.yoasfebrianus.data.pojo.UserPojo
import comtest.ct.cd.yoasfebrianus.di.UserDispatcherProvider
import comtest.ct.cd.yoasfebrianus.util.viewmodel.BaseViewModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserListModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val baseDispatcher: UserDispatcherProvider,
    private val userRemoteDataSource: UserRemoteDataSource)
    : BaseViewModel(baseDispatcher.ui()){

    val userDataResp: LiveData<Result<UserListModel>>
        get() = _userDataResp
    private val _userDataResp = MutableLiveData<Result<UserListModel>>()


    val userDataMoreResp: LiveData<Result<UserListModel>>
        get() = _userDataMoreResp
    private val _userDataMoreResp = MutableLiveData<Result<UserListModel>>()


    fun getUserData() {
        viewModelScope.launch(baseDispatcher.io()) {
            try {
                val data = userRemoteDataSource.fetchData()
                var userData = UserListModel()
                data.body()?.let {
                    userData = mapUserdata(it.userList)
                }
                if (userData.dataList.isNotEmpty()) {
                    _userDataResp.value = Result.success(userData)
                } else {
                    _userDataResp.value = Result.failure(Exception("empty data"))
                }
            } catch (e: Exception) {
                _userDataResp.value = Result.failure(e)
            }
        }
    }

    private fun mapUserdata(dataList: List<UserPojo>): UserListModel {
        val userList = mutableListOf<UserModel>()
        for (data in dataList) {
            userList.add(
                UserModel(
                    id = data.id.toString(),
                    imageUrl = data.avatarUrl,
                    userName = data.login
                )
            )
        }
        return UserListModel(userList)
    }

}