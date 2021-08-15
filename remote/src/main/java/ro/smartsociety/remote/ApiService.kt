package ro.smartsociety.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ro.smartsociety.remote.model.GetProductsResponse

interface ApiService {

    @GET("/v2/availability/products/")
    suspend fun getProducts(
        @Query("locale") locale: String?,
        @Query("shipping_country_id") shipping_country_id: String?,
        @Query("first_item_in_purchase") first_item_in_purchase: String?,
    ): GetProductsResponse
}
