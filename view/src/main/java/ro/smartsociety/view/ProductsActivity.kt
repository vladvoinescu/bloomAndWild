package ro.smartsociety.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import ro.smartsociety.view.composable.ProductList
import ro.smartsociety.view.theme.BloomAndWildTheme

@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: ProductsViewModel by viewModels()

        setContent {
            BloomAndWildTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ProductList(viewModel = viewModel)
                }
            }
        }
    }
}
