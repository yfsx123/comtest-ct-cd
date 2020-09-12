package comtest.ct.cd.yoasfebrianus.di

import comtest.ct.cd.yoasfebrianus.view.activity.MainActivity
import dagger.Component
import retrofit2.Retrofit

@UserScope
@Component(modules = [UserModule::class, ViewModelModule::class])
interface UserComponent {

    fun inject(mainActivity: MainActivity)

    fun retrofitBuilder(): Retrofit.Builder
}