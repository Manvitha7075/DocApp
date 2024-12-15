package com.test.manvitha.repository

import com.test.manvitha.models.Address
import com.test.manvitha.models.SearchRequest
import com.test.manvitha.models.SearchResponse
import com.test.manvitha.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DoctorSearchRepository {
    suspend fun searchByZip(zip: String): SearchResponse
}

class DoctorSearchRepositoryImpl(private val apiService: ApiService) : DoctorSearchRepository {
    override suspend fun searchByZip(zip: String): SearchResponse {
        val request = SearchRequest(address = Address(zip))
        return withContext(Dispatchers.IO) {
            apiService.searchByZip(request)
        }
    }
}
