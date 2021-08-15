package ro.smartsociety.bloomandwild.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object CoroutineDispatcherModule {

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO
}
