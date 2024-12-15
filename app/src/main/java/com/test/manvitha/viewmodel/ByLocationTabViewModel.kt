package com.test.manvitha.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.manvitha.models.Location
import com.test.manvitha.repository.DoctorSearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SearchResultState {
    data object Loading : SearchResultState()
    data class Success(val locations: List<Location>) : SearchResultState()
    data class Error(val message: String) : SearchResultState()
    data object NoResults : SearchResultState()
    data object EmptyState : SearchResultState()
}

class ByLocationTabViewModel(private val doctorSearchRepository: DoctorSearchRepository) :
    ViewModel() {

    private val _selectedNetwork = MutableStateFlow("Choose your network")
    val selectedNetwork: StateFlow<String> = _selectedNetwork

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _searchResultState =
        MutableStateFlow<SearchResultState>(SearchResultState.NoResults)
    val searchResultState: StateFlow<SearchResultState> = _searchResultState

    private val _showCountyAlert = MutableStateFlow(false)
    val showCountyAlert: StateFlow<Boolean> = _showCountyAlert


    fun showCountyAlert(alert: Boolean) {
        _showCountyAlert.value = alert
    }

    fun onNetworkSelected(network: String) {
        _selectedNetwork.value = network
    }

    private fun validateZipCode(zipCode: String): Boolean {
        return if (zipCode.length != 5 || !zipCode.all { it.isDigit() }) {
            _errorMessage.value = "Invalid ZIP Code. Please enter a 5-digit number."
            false
        } else {
            true
        }
    }

    fun searchByZip(zipCode: String) {
        if (!validateZipCode(zipCode)) return

        viewModelScope.launch {
            _searchResultState.value = SearchResultState.Loading
            _errorMessage.value = ""
            try {
                val locations =
                    doctorSearchRepository.searchByZip(zipCode).zipClassSortedLocations.orEmpty()
                _searchResultState.value = if (locations.isEmpty()) {
                    SearchResultState.EmptyState
                } else {
                    SearchResultState.Success(locations)
                }

            } catch (e: Exception) {
                _searchResultState.value =
                    SearchResultState.Error("Unexpected error occurred: ${e.localizedMessage}")
            }
        }
    }
}
