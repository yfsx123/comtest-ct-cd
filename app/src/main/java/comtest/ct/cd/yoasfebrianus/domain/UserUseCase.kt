package comtest.ct.cd.yoasfebrianus.domain

import comtest.ct.cd.yoasfebrianus.data.datasource.UserRemoteDataSource
import comtest.ct.cd.yoasfebrianus.data.pojo.UserPojo
import comtest.ct.cd.yoasfebrianus.util.usecase.RequestParams
import comtest.ct.cd.yoasfebrianus.util.usecase.UseCase
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserListModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserModel

class UserUseCase(
    private val userRemoteDataSource: UserRemoteDataSource
): UseCase<UserListModel>(){

    companion object {
        const val REQ_KEYWORD = "keyword"
    }

    override suspend fun executeOnBackground(): UserListModel {
        val result = userRemoteDataSource.fetchData(useCaseRequestParams.getString(REQ_KEYWORD, ""))
        result.body()?.let {
            return mapUserdata(it.userList)
        }
        return UserListModel()
    }

    fun setParams(keyword: String) {
        useCaseRequestParams = RequestParams.EMPTY
        val params = RequestParams.create()
        params.putString(REQ_KEYWORD, keyword)
        useCaseRequestParams = params
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