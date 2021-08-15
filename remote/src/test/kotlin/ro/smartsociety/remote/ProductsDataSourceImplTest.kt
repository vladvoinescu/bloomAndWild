package ro.smartsociety.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ro.smartsociety.data.model.ProductEntity
import ro.smartsociety.data.model.Response
import ro.smartsociety.remote.datasource.EMPTY_PRODUCT_LIST
import ro.smartsociety.remote.datasource.ProductsDataSourceImpl
import ro.smartsociety.remote.model.Attributes
import ro.smartsociety.remote.model.DataItem
import ro.smartsociety.remote.model.GetProductsResponse
import ro.smartsociety.remote.model.PriceDataItem

@ExperimentalCoroutinesApi
internal class ProductsDataSourceImplTest {

    private val apiService: ApiService = mock()
    private val dataSource = ProductsDataSourceImpl(apiService, TestCoroutineDispatcher())

    @Test
    fun getProducts_success() {
        val mockName = "product"
        val mockPrice = 1
        val input = GetProductsResponse(listOf(DataItem(Attributes(listOf(PriceDataItem(mockPrice)), mockName))))
        val expected = Response.Success(listOf(ProductEntity(mockName, mockPrice)))

        runBlocking {
            whenever(apiService.getProducts()).thenReturn(input)

            val actual = dataSource.getProducts()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getProducts_failure_emptyList() {
        val input = GetProductsResponse(emptyList())
        val expected = EMPTY_PRODUCT_LIST

        runBlocking {
            whenever(apiService.getProducts()).thenReturn(input)

            val actual = dataSource.getProducts()
            assertEquals(expected, (actual as Response.Failure).error?.message)
        }
    }

    @Test
    fun getProducts_failure_exception() {
        val exception = RuntimeException()
        val expected = Response.Failure(exception)

        runBlocking {
            whenever(apiService.getProducts()).thenThrow(exception)

            val actual = dataSource.getProducts()
            assertEquals(expected, actual)
        }
    }
}

private suspend fun ApiService.getProducts() = getProducts(
    locale = "en",
    shipping_country_id = "1",
    first_item_in_purchase = "true"
)
