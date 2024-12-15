package com.test.manvitha.network

import com.test.manvitha.models.SearchRequest
import com.test.manvitha.models.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("eyedoclocator/api/v1/search")
    suspend fun searchByZip(@Body payload: SearchRequest): SearchResponse
}
