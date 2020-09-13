package comtest.ct.cd.yoasfebrianus.data.datasource

import comtest.ct.cd.yoasfebrianus.data.api.UserService
import comtest.ct.cd.yoasfebrianus.data.pojo.UserPojo
import comtest.ct.cd.yoasfebrianus.data.pojo.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val service: UserService) {

    suspend fun fetchData(keyword: String): Response<UserResponse> {
        return service.getUserFirst(keyword)
    }
}