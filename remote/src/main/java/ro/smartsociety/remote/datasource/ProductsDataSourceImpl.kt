package ro.smartsociety.remote.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ro.smartsociety.data.ProductsDataSource
import ro.smartsociety.data.model.ProductEntity
import ro.smartsociety.data.model.Response
import ro.smartsociety.remote.ApiService
import javax.inject.Inject

const val EMPTY_PRODUCT_LIST = "The products list is empty."

class ProductsDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val coroutineDispatcher: CoroutineDispatcher
) : ProductsDataSource {

    override suspend fun getProducts(): Response<List<ProductEntity>> {
        return withContext(coroutineDispatcher) {
            try {
                val products = apiService.getValidProducts()

                if (products.isEmpty()) {
                    return@withContext Response.Failure(Throwable(EMPTY_PRODUCT_LIST))
                }

                Response.Success(
                    apiService.getValidProducts().map {
                        ProductEntity(it!!.attributes!!.name!!, it.attributes!!.priceData!![0]!!.pricePennies!!)
                    }
                )
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }

    private suspend fun ApiService.getValidProducts() = getProducts(
        locale = "en",
        shipping_country_id = "1",
        first_item_in_purchase = "true"
    ).data?.filter {
        it != null
        it!!.attributes != null
        it.attributes!!.name != null
        it.attributes.priceData != null
        it.attributes.priceData!![0] != null
        it.attributes.priceData[0]!!.pricePennies != null
    } ?: emptyList()
}
