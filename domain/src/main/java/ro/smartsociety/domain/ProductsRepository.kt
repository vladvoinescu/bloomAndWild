package ro.smartsociety.domain

import kotlinx.coroutines.flow.Flow
import ro.smartsociety.domain.model.Product

interface ProductsRepository {

    fun getProducts(onStart: () -> Unit, onSuccess: () -> Unit, onError: (String) -> Unit): Flow<List<Product>>
}
