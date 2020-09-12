package comtest.ct.cd.yoasfebrianus.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import comtest.ct.cd.yoasfebrianus.data.api.UserService
import comtest.ct.cd.yoasfebrianus.data.datasource.UserRemoteDataSource
import comtest.ct.cd.yoasfebrianus.domain.UserUseCase
import comtest.ct.cd.yoasfebrianus.util.network.Api.API_URL
import comtest.ct.cd.yoasfebrianus.util.network.OkHttpRetryPolicy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val NET_READ_TIMEOUT = 60
private const val NET_WRITE_TIMEOUT = 60
private const val NET_CONNECT_TIMEOUT = 60
private const val NET_RETRY = 1

@Module
class UserModule {

    private val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    @UserScope
    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @UserScope
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat(GSON_DATE_FORMAT)
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    @UserScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        retrofitBuilder: Retrofit.Builder): Retrofit {
        return retrofitBuilder.baseUrl(API_URL).client(okHttpClient).build()
    }

    @UserScope
    @Provides
    fun provideOkHttpClient(retryPolicy : OkHttpRetryPolicy): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(retryPolicy.connectTimeout.toLong(), TimeUnit.SECONDS)
            .connectTimeout(retryPolicy.readTimeout.toLong(), TimeUnit.SECONDS)
            .connectTimeout(retryPolicy.writeTimeout.toLong(), TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @UserScope
    @Provides
    fun provideRetryPolicy() :OkHttpRetryPolicy {
        return OkHttpRetryPolicy(
            NET_READ_TIMEOUT,
            NET_WRITE_TIMEOUT,
            NET_CONNECT_TIMEOUT,
            NET_RETRY)
    }

    @UserScope
    @Provides
    fun providesUserProvider(): UserDispatcherProvider {
        return UserProdDispatcherProvider()
    }

    @UserScope
    @Provides
    fun providesUserUseCase(userRemoteDataSource: UserRemoteDataSource): UserUseCase {
        return UserUseCase(userRemoteDataSource)
    }

    @UserScope
    @Provides
    fun provideUserRemoteDataSource(userService: UserService): UserRemoteDataSource{
        return UserRemoteDataSource(userService)
    }

    @UserScope
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}