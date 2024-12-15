// src/test/kotlin/com/test/manvitha/repository/FakeDoctorSearchRepository.kt

package com.test.manvitha.fake

import com.test.manvitha.models.SearchResponse
import com.test.manvitha.repository.DoctorSearchRepository

class FakeDoctorSearchRepository : DoctorSearchRepository {

    private val fakeResponses = mutableMapOf<String, SearchResponse>()
    private val fakeExceptions = mutableMapOf<String, Exception>()

    /**
     * Allows setting a fake response for a given ZIP code.
     */
    fun setFakeResponse(zip: String, response: SearchResponse) {
        fakeResponses[zip] = response
    }

    /**
     * Allows setting a fake exception to be thrown for a given ZIP code.
     */
    fun setFakeException(zip: String, exception: Exception) {
        fakeExceptions[zip] = exception
    }

    override suspend fun searchByZip(zip: String): SearchResponse {
        // First, check if an exception is set for the given ZIP code
        fakeExceptions[zip]?.let { throw it }

        // Return the fake response if available
        return fakeResponses[zip] ?: SearchResponse(
            message = "No Data",
            messageCode = "404",
            zipClassSortedLocations = emptyList()
        )
    }
}