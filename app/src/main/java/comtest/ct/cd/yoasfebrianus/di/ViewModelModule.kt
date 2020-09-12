package comtest.ct.cd.yoasfebrianus.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import comtest.ct.cd.yoasfebrianus.util.viewmodel.ViewModelFactory
import comtest.ct.cd.yoasfebrianus.view.presenter.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun provideUserViewModel(viewModel: UserViewModel): ViewModel
}