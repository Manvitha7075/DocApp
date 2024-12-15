package com.test.manvitha.viewmodel

import com.test.manvitha.models.Location
import com.test.manvitha.models.LocationAddress
import com.test.manvitha.models.SearchResponse
import com.test.manvitha.fake.FakeDoctorSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class) // Use MockitoJUnitRunner for JUnit 4
class ByLocationTabViewModelTest {

    private lateinit var fakeRepository: FakeDoctorSearchRepository
    private lateinit var viewModel: ByLocationTabViewModel

    // Use a TestDispatcher for controlling coroutine execution
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeDoctorSearchRepository()
        viewModel = ByLocationTabViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchByZip with valid zip code returns success result`() = runTest {
        // Arrange
        val validZipCode = "12345"
        val mockLocations = listOf(
            Location(
                businessName = "Test Location",
                distance = 10.0,
                latitude = 40.0,
                longitude = -70.0,
                locationId = "loc1",
                doctors = emptyList(),
                address = LocationAddress(
                    city = "Test City",
                    state = "Test State",
                    streetAddress1 = "Test Address 1",
                    zip = "12345"
                )
            )
        )
        val mockResponse = SearchResponse(
            message = "Success",
            messageCode = "200",
            zipClassSortedLocations = mockLocations
        )

        fakeRepository.setFakeResponse(validZipCode, mockResponse)

        // Act
        viewModel.searchByZip(validZipCode)
        // Advance coroutines until idle to ensure all coroutines have completed
        advanceUntilIdle()

        // Assert
        val result = viewModel.searchResultState.first()
        assertTrue(result is SearchResultState.Success)
        assertEquals(mockLocations, (result as SearchResultState.Success).locations)
    }

    @Test
    fun `searchByZip with valid zip code but empty result should return empty result state`() =
        runTest {
            // Arrange
            val validZipCode = "12345"
            val mockResponse = SearchResponse(
                message = "Success",
                messageCode = "200",
                zipClassSortedLocations = emptyList()
            )
            fakeRepository.setFakeResponse(validZipCode, mockResponse)

            // Act
            viewModel.searchByZip(validZipCode)
            advanceUntilIdle()

            // Assert
            val result = viewModel.searchResultState.first()
            assertTrue(result is SearchResultState.EmptyState)
        }

    @Test
    fun `searchByZip with invalid zip code should set error message`() = runTest {
        // Arrange
        val invalidZipCode = "1234"
        // Assuming the ViewModel validates the zip code before calling repository,
        // no need to set a response in the fake repository

        // Act
        viewModel.searchByZip(invalidZipCode)
        advanceUntilIdle()

        // Assert
        val error = viewModel.errorMessage.first()
        assertEquals("Invalid ZIP Code. Please enter a 5-digit number.", error)
    }

    @Test
    fun `searchByZip with exception should return error result`() = runTest {
        // Arrange
        val validZipCode = "12345"
        val exceptionMessage = "Network error"
        // Simulate an error by setting the repository to throw an exception
        fakeRepository.setFakeException(validZipCode, RuntimeException(exceptionMessage))

        // Act
        viewModel.searchByZip(validZipCode)
        advanceUntilIdle()

        // Assert
        val result = viewModel.searchResultState.first()
        assertTrue(result is SearchResultState.Error)
        assertEquals(
            "Unexpected error occurred: $exceptionMessage",
            (result as SearchResultState.Error).message
        )
    }

    @Test
    fun `onNetworkSelected should update selected network value`() = runTest {
        // Arrange
        val newNetwork = "New Network"

        // Act
        viewModel.onNetworkSelected(newNetwork)
        advanceUntilIdle()

        // Assert
        val selectedNetwork = viewModel.selectedNetwork.first()
        assertEquals(newNetwork, selectedNetwork)
    }

    @Test
    fun `showCountyAlert should update showCountyAlert value`() = runTest {
        // Arrange
        val showAlertDialog = true

        // Act
        viewModel.showCountyAlert(showAlertDialog)
        advanceUntilIdle()

        // Assert
        val result = viewModel.showCountyAlert.first()
        assertEquals(showAlertDialog, result)
    }
}