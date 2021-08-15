package ro.smartsociety.view.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ro.smartsociety.domain.model.Product
import ro.smartsociety.view.ProductsViewModel

@Composable
fun ProductList(
    viewModel: ProductsViewModel,
    modifier: Modifier = Modifier
) {
    val products: List<Product> by viewModel.productList.observeAsState(listOf())
    val isLoading: Boolean by viewModel.isLoading.observeAsState(false)
    val context = LocalContext.current

    ConstraintLayout {
        val (body, progress) = createRefs()

        Surface(
            modifier = modifier
                .constrainAs(body) {
                    top.linkTo(parent.top)
                }
                .padding(16.dp),
        ) {
            val listState = rememberLazyListState()
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(4.dp)
            ) {
                items(
                    items = products,
                    itemContent = { product ->
                        ProductRow(
                            product = product
                        )
                    }
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(progress) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .visible(isLoading)
            )
        }
    }

    viewModel.toast.observe(LocalLifecycleOwner.current) {
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
    }
}

@Stable
private fun Modifier.visible(visibility: Boolean): Modifier {
    return if (visibility) {
        this.then(alpha(1f))
    } else {
        this.then(alpha(0f))
    }
}
