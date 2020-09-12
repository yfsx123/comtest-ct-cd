package comtest.ct.cd.yoasfebrianus.di

import android.content.Context
import comtest.ct.cd.yoasfebrianus.view.activity.MainActivity
import dagger.Component

@UserScope
@Component(modules = [UserModule::class])
interface UserComponent {

    fun inject(mainActivity: MainActivity)
}