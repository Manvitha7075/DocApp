package com.test.manvitha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.test.manvitha.ui.theme.DocAppTheme
import com.test.manvitha.ui.view.ByLocationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DocAppTheme {
                MainScreen()
            }
        }
    }
}

data class TabItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val selectedTab = remember { mutableIntStateOf(0) }

    val tabs = listOf(
        TabItem(
            stringResource(R.string.tab_by_location),
            Icons.Filled.LocationOn,
            MaterialTheme.colorScheme.primary
        ),
        TabItem(stringResource(R.string.tab_by_doctor), Icons.Filled.Person, Color(0xFF8E44AD)),
        TabItem(
            stringResource(R.string.tab_online_lasik),
            Icons.Filled.MailOutline,
            Color(0xFFE74C3C)
        )
    )

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.app_title)) },
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary
            )
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = selectedTab.intValue) {
                tabs.forEachIndexed { index, tabItem ->
                    Tab(
                        text = {
                            Column( horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = tabItem.icon, contentDescription = null, tint = tabItem.iconColor)
                                Text(text = tabItem.title, color = tabItem.iconColor)
                            }
                        },
                        selected = selectedTab.intValue == index,
                        onClick = { selectedTab.intValue = index }
                    )
                }
            }
            when (selectedTab.intValue) {
                0 -> ByLocationScreen()
                1 -> ByDoctorScreen()
                2 -> OnlineAndLasikScreen()
            }
        }
    }
}


@Composable
fun ByDoctorScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.by_doctor))
    }
}

@Composable
fun OnlineAndLasikScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.online_lasik))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DocAppTheme {
        MainScreen()
    }
}