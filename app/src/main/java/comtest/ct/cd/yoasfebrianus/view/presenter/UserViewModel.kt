package comtest.ct.cd.yoasfebrianus.view.presenter

import comtest.ct.cd.yoasfebrianus.di.UserDispatcherProvider
import comtest.ct.cd.yoasfebrianus.util.BaseViewModel
import javax.inject.Inject

class UserViewModel @Inject constructor(baseDispatcher: UserDispatcherProvider)
    :BaseViewModel(baseDispatcher.ui()){


}