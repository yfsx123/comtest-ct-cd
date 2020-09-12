package comtest.ct.cd.yoasfebrianus.di


import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class UserModule {

    @UserScope
    @Provides
    fun providesUserProvider(): UserDispatcherProvider {
        return UserProdDispatcherProvider()
    }
}