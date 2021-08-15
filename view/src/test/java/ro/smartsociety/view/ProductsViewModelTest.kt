package ro.smartsociety.view

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ro.smartsociety.domain.ProductsRepository
import ro.smartsociety.domain.model.Product

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class ProductsViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutinesExtension = CoroutinesTestExtension()

    @Captor
    private lateinit var captor: ArgumentCaptor<List<Product>>

    private val repository: ProductsRepository = mock()
    private val viewModel = ProductsViewModel(repository, coroutinesExtension.dispatcher)

    private val mockObserver: Observer<List<Product>> = mock()

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getProductList() = runBlockingTest {
        val input = listOf(Product("product", 1))
        val flow = flow {
            emit(input)
        }
        whenever(repository.getProducts(any(), any(), any())).thenReturn(flow)

        viewModel.productList.observeForever(mockObserver)
        verify(mockObserver).onChanged(captor.capture())
        assertEquals(input, captor.value)
    }
}

class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true
            })
    }

    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}

@ExperimentalCoroutinesApi
class CoroutinesTestExtension(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : BeforeEachCallback, AfterEachCallback, TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
