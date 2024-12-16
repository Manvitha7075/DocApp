# DocApp

## Overview
DocApp is an Android application built using **Jetpack Compose** and follows the **MVVM architecture**. 
The application features a tabbed UI with functionalities such as searching for doctors by location, zip code, or county. 

## Features
- **Tabbed Navigation**: 
  - **By Location**: Search doctors using zip code or county.
  - **By Doctor**: Displays a placeholder for the doctor search.
  - **Online & Lasik**: Displays a placeholder for Lasik-related information.
  
- **Search By ZIP**:
  - Validates the entered ZIP code.
  - Makes an HTTP POST request to fetch results.
  - Logs the response in the console.

- **Search By County**: Displays an alert dialog on tap.

- **Use My Location**: Requests the device location using Android APIs.

## Tech Stack
- **Programming Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit + Moshi
- **Dependency Injection**: Koin
- **Coroutines**: For managing background tasks.


## Project Structure
**MainActivity**
- Hosts the main UI with three tabs.

**Composable Screens**
- ByLocationScreen: Handles "By Location" functionality.
- ByDoctorScreen: Displays a placeholder for "By Doctor".
- OnlineAndLasikScreen: Displays a placeholder for "Online & Lasik".

**ViewModel**
- ByLocationTabViewModel:
- Manages UI state for "By Location".
- Handles validation and networking tasks.

**Repository**
- DoctorSearchRepository: Abstract interface for data operations.
- DoctorSearchRepositoryImpl: Implements data fetching logic using Retrofit.

**Data Class**
- SearchRequest: Represents the request payload for the API.
- SearchResponse: Models the API response.
- Supporting models: Address, Location, Doctor.

**Dependency Injection (DI)**
- Koin appModule provides:
- ApiService: Configured with Retrofit and Moshi.
- DoctorSearchRepository: Repository instance.
- ByLocationTabViewModel: ViewModel instance.

**Networking**
- API requests are managed in ApiService using Retrofit.


## Running the App
- Run the app on an emulator or device.
- Navigate through the tabs to explore functionalities.
- Test ZIP code search: Enter a valid 5-digit ZIP code. Tap "Search by ZIP". View the logs for API response.
- Test other features: Tap "Search by County" to display the alert.
- Tap "Use My Location" to request location permissions.
