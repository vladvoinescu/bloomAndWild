package ro.smartsociety.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ro.smartsociety.data.model.ProductEntity
import ro.smartsociety.data.model.Response
import ro.smartsociety.data.repository.ProductsRepositoryImpl
import ro.smartsociety.data.repository.TECHNICAL_ERROR
import ro.smartsociety.domain.model.Product

@ExperimentalCoroutinesApi
internal class ProductsRepositoryImplTest {

    private val dataSource: ProductsDataSource = mock()
    private val repository = ProductsRepositoryImpl(dataSource, TestCoroutineDispatcher())

    @Test
    fun getProducts_success() {
        val mockName = "product"
        val mockPrice = 1

        val input = listOf(ProductEntity(mockName, mockPrice))
        val expected = listOf(Product(mockName, mockPrice))

        var onStartCalled = false
        var onSuccessCalled = false
        var onErrorCalled = false

        runBlocking {
            whenever(dataSource.getProducts()).thenReturn(Response.Success(input))

            val actual = repository.getProducts(
                onStart = { onStartCalled = true },
                onSuccess = { onSuccessCalled = true },
                onError = { onErrorCalled = true }
            ).first()

            assertEquals(true, onStartCalled)
            assertEquals(true, onSuccessCalled)
            assertEquals(false, onErrorCalled)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun getProducts_failure_specificError() {
        val specificError = "specificError"
        val input = Throwable(specificError)

        var result = ""

        runBlocking {
            whenever(dataSource.getProducts()).thenReturn(Response.Failure(input))

            repository.getProducts(
                onStart = {},
                onSuccess = {},
                onError = { result = it }
            ).collect()

            assertEquals(specificError, result)
        }
    }

    @Test
    fun getProducts_failure_generalError() {
        val input = Throwable()

        var result = ""

        runBlocking {
            whenever(dataSource.getProducts()).thenReturn(Response.Failure(input))

            repository.getProducts(
                onStart = {},
                onSuccess = {},
                onError = { result = it }
            ).collect()

            assertEquals(TECHNICAL_ERROR, result)
        }
    }
}
