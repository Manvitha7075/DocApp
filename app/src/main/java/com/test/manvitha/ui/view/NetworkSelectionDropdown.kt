package com.test.manvitha.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.test.manvitha.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkSelectionDropdown(
    selectedNetwork: String,
    onNetworkSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val networks = stringArrayResource(id = R.array.network_options).toList()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.select_network_label),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedNetwork,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text(text = stringResource(R.string.select_network_placeholder)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                shape = RectangleShape,
                singleLine = true
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                networks.forEach { network ->
                    DropdownMenuItem(
                        text = { Text(network) },
                        onClick = {
                            onNetworkSelected(network)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}