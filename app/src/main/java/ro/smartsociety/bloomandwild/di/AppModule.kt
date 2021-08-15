package ro.smartsociety.bloomandwild.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ro.smartsociety.data.ProductsDataSource
import ro.smartsociety.data.repository.ProductsRepositoryImpl
import ro.smartsociety.domain.ProductsRepository
import ro.smartsociety.remote.datasource.ProductsDataSourceImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindProductsRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository

    @Binds
    abstract fun bindProductsDataSource(
        productsDataSourceImpl: ProductsDataSourceImpl
    ): ProductsDataSource
}
