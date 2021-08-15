package ro.smartsociety.view.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ro.smartsociety.domain.model.Product

@Composable
fun ProductRow(
    product: Product,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        color = MaterialTheme.colors.primary,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(8.dp)
        ) {
            val (name, price) = createRefs()
            Text(
                text = product.name,
                modifier = Modifier
                    .constrainAs(name) {
                        bottom.linkTo(price.top)
                    }
            )
            Text(
                text = product.price.toString() + "\$",
                modifier = Modifier
                    .constrainAs(price) {
                        top.linkTo(name.bottom)
                    }
            )
        }
    }
}
