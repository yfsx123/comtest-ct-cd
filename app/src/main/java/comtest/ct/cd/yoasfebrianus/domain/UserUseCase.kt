package comtest.ct.cd.yoasfebrianus.domain

import comtest.ct.cd.yoasfebrianus.data.datasource.UserRemoteDataSource
import comtest.ct.cd.yoasfebrianus.data.pojo.UserPojo
import comtest.ct.cd.yoasfebrianus.util.usecase.UseCase
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserListModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserModel

class UserUseCase(
    private val userRemoteDataSource: UserRemoteDataSource
): UseCase<UserListModel>(){

    override suspend fun executeOnBackground(): UserListModel {
        val result = userRemoteDataSource.fetchData()
        result.body()?.let {
            return mapUserdata(it.userList)
        }
        return UserListModel()
    }

    private fun mapUserdata(dataList: List<UserPojo>): UserListModel {
        val userList = mutableListOf<UserModel>()
        for (data in dataList) {
            userList.add(UserModel(
                id = data.id.toString(),
                imageUrl = data.avatarUrl,
                userName = data.login))
        }
        return UserListModel(userList)
    }
}