package comtest.ct.cd.yoasfebrianus.di

import kotlinx.coroutines.CoroutineDispatcher

interface UserDispatcherProvider {
    fun io() : CoroutineDispatcher
    fun ui() : CoroutineDispatcher
}