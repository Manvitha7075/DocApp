package com.test.manvitha.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRequest(
    val tieredView: Boolean = false,
    val sortByZipClass: Boolean = true,
    val clientId: String = "member",
    val address: Address
)

@JsonClass(generateAdapter = true)
data class Address(val zip: String)

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val message: String,
    val messageCode: String,
    val zipClassSortedLocations: List<Location>?,
)

@JsonClass(generateAdapter = true)
data class Location(
    val address: LocationAddress,
    val businessName: String,
    val distance: Double,
    val latitude: Double,
    val longitude: Double,
    val locationId: String,
    val doctors: List<Doctor>?,
)

@JsonClass(generateAdapter = true)
data class LocationAddress(
    val city: String,
    val state: String,
    val streetAddress1: String,
    val zip: String,
)

@JsonClass(generateAdapter = true)
data class Doctor(
    val acceptingNewPatients: String,
    val firstName: String,
    val gender: String?,
    val languagesSpoken: String?,
    val lastName: String,
    val middleName: String?,
)