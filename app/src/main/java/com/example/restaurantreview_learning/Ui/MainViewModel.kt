package com.example.restaurantreview_learning.Ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview_learning.data.response.CustomerReviewsItem
import com.example.restaurantreview_learning.data.response.PostReviewResponse
import com.example.restaurantreview_learning.data.response.Restaurant
import com.example.restaurantreview_learning.data.response.RestaurantResponse
import com.example.restaurantreview_learning.data.retrofit.ApiConfig
import com.example.restaurantreview_learning.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant : LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview : LiveData<List<CustomerReviewsItem>> = _listReview

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackBack : LiveData<Event<String>> = _snackBar

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"

    }

    init {
        findRestaurant()

    }

    private fun findRestaurant() {
        _loading.value = true
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    _restaurant.value = response.body()?.restaurant
                    _listReview.value = response.body()?.restaurant?.customerReviews
                    _snackBar.value = Event(response.body()?.message.toString())
                }else {
                    Log.e(TAG,"onfailure: ${response.message()}")

            }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onfailure : ${t.message.toString()}")
            }
        })
        }

    fun postReview(review: String) {
        _loading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Afdhola-nabil", review)
        client.enqueue(object : Callback<PostReviewResponse> {
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                   _listReview.value = response.body()?.customerReviews
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}






    
