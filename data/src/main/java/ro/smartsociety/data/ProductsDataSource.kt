package ro.smartsociety.data

import ro.smartsociety.data.model.ProductEntity
import ro.smartsociety.data.model.Response

interface ProductsDataSource {

    suspend fun getProducts(): Response<List<ProductEntity>>
}
