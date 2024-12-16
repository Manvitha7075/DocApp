package com.test.manvitha.ui.view

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.test.manvitha.R
import com.test.manvitha.viewmodel.ByLocationTabViewModel
import com.test.manvitha.viewmodel.SearchResultState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ByLocationScreen(viewModel: ByLocationTabViewModel = koinViewModel()) {
    val errorMessage by viewModel.errorMessage.collectAsState()
    val selectedNetwork by viewModel.selectedNetwork.collectAsState()
    val showCountyAlert by viewModel.showCountyAlert.collectAsState()
    val searchResultState by viewModel.searchResultState.collectAsState()
    val context = LocalContext.current
    val itemHeight = 48.dp

    var showPermissionDialog by remember { mutableStateOf(false) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            showPermissionDialog = true
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.location_permission_granted),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        NetworkSelectionDropdown(
            selectedNetwork = selectedNetwork,
            onNetworkSelected = { viewModel.onNetworkSelected(it) }
        )

        SearchByZipUI(onSearch = { viewModel.searchByZip(it) })


        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        when (searchResultState) {
            is SearchResultState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is SearchResultState.Error -> {
                Text(
                    text = (searchResultState as SearchResultState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is SearchResultState.EmptyState -> {
                Text(text = "No results found", color = MaterialTheme.colorScheme.error)
            }

            is SearchResultState.Success -> {
                val locations = (searchResultState as SearchResultState.Success).locations
                Text(
                    text = "Location found count= ${locations.size}, more in console",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            else -> Unit
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .clickable { viewModel.showCountyAlert(true) }

        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = stringResource(R.string.search_by_county),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.search_by_county),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,

                )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.or_text),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Button(
            onClick = {
                locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight),
            shape = RectangleShape
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = stringResource(R.string.use_my_location),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = stringResource(R.string.use_my_location),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        AlertDialog(
            showDialog = showCountyAlert,
            title = "Alert",
            message = "Search by county tapped.",
            onDismiss = { viewModel.showCountyAlert(false) }
        )

        PermissionDialog(
            showDialog = showPermissionDialog,
            onDismiss = { showPermissionDialog = false },
            onConfirm = { showPermissionDialog = false }
        )
    }
}