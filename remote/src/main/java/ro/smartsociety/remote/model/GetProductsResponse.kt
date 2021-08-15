package ro.smartsociety.remote.model

import com.google.gson.annotations.SerializedName

data class GetProductsResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null
)

data class Attributes(

    @field:SerializedName("price_data")
    val priceData: List<PriceDataItem?>? = null,

    @field:SerializedName("name")
    val name: String? = null,
)

data class DataItem(

    @field:SerializedName("attributes")
    val attributes: Attributes? = null,
)

data class PriceDataItem(

    @field:SerializedName("price_pennies")
    val pricePennies: Int? = null
)
