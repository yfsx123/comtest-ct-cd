package comtest.ct.cd.yoasfebrianus.data.api

import comtest.ct.cd.yoasfebrianus.data.pojo.UserPojo
import comtest.ct.cd.yoasfebrianus.data.pojo.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface UserService {

    @GET("/search/users?q=a")
    suspend fun getUserFirst(): Response<UserResponse>
}