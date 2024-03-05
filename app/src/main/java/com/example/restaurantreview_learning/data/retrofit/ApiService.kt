package com.example.restaurantreview_learning.data.retrofit


import com.example.restaurantreview_learning.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("detail/{id}")
    fun getRestaureant(
        @Path("id") id : String

    ):Call<RestaurantResponse>
}