package ro.smartsociety.view

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import ro.smartsociety.domain.ProductsRepository
import ro.smartsociety.domain.model.Product
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String> get() = _toast

    private val _productList: MutableLiveData<Boolean> = MutableLiveData(true)
    val productList: LiveData<List<Product>> = _productList.switchMap {
        productsRepository.getProducts(
            onStart = { _isLoading.postValue(true) },
            onSuccess = { _isLoading.postValue(false) },
            onError = { _toast.postValue(it) }
        ).asLiveData(coroutineDispatcher)
    }
}

