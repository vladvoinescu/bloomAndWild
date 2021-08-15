package ro.smartsociety.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import ro.smartsociety.data.ProductsDataSource
import ro.smartsociety.data.model.Response
import ro.smartsociety.domain.ProductsRepository
import ro.smartsociety.domain.model.Product
import javax.inject.Inject

const val TECHNICAL_ERROR = "A technical error has occurred, please try again later."

class ProductsRepositoryImpl @Inject constructor(
    private val productsDataSource: ProductsDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : ProductsRepository {

    override fun getProducts(
        onStart: () -> Unit,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        when (val result = productsDataSource.getProducts()) {
            is Response.Success -> emit(
                result.value.map {
                    Product(it.name, it.price)
                }
            )
            is Response.Failure -> onError(result.error?.message ?: TECHNICAL_ERROR)
        }
    }.onStart { onStart() }.onCompletion { onSuccess() }.flowOn(coroutineDispatcher)
}
