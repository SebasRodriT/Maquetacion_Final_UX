package com.example.smartalarmsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.smartalarmsapp.ui.components.BottomNavBar
import com.example.smartalarmsapp.ui.navigation.Screen
import com.example.smartalarmsapp.ui.screens.AlarmsScreen
import com.example.smartalarmsapp.ui.screens.CreateAlarmScreen
import com.example.smartalarmsapp.ui.screens.SettingsScreen
import com.example.smartalarmsapp.ui.screens.SplashScreen
import com.example.smartalarmsapp.ui.screens.SuggestionsScreen
import com.example.smartalarmsapp.ui.theme.AppBackground
import com.example.smartalarmsapp.ui.theme.AppText
import com.example.smartalarmsapp.ui.theme.SmartAlarmsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartAlarmsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppBackground
                ) {
                    AppHost()
                }
            }
        }
    }
}

@Composable
fun AppHost() {
    var backStack by remember { mutableStateOf(listOf(Screen.SPLASH)) }
    val currentScreen = backStack.last()

    BackHandler(enabled = backStack.size > 1) {
        backStack = backStack.dropLast(1)
    }

    val navigateTo: (Screen) -> Unit = { screen -> backStack = backStack + screen }
    val navigateToTab: (Screen) -> Unit = { screen -> backStack = listOf(screen) }

    when (currentScreen) {
        Screen.SPLASH -> SplashScreen(onStart = { navigateTo(Screen.ALARMS) })
        Screen.ALARMS -> AlarmsScreen(
            currentScreen = currentScreen,
            onCreateAlarm = { navigateTo(Screen.CREATE_ALARM) },
            onEditAlarm = { navigateTo(Screen.EDIT_ALARM) },
            onTabSelected = navigateToTab
        )
        Screen.CREATE_ALARM -> CreateAlarmScreen(
            onCancel = { backStack = backStack.dropLast(1) },
            onSave = { backStack = backStack.dropLast(1) },
            onTabSelected = navigateToTab
        )
        Screen.EDIT_ALARM -> CreateAlarmScreen(
            isEditMode = true,
            onCancel = { backStack = backStack.dropLast(1) },
            onSave = { backStack = backStack.dropLast(1) },
            onTabSelected = navigateToTab
        )
        Screen.SUGGESTIONS -> SuggestionsScreen(
            onCreateAlarm = { navigateTo(Screen.CREATE_ALARM) },
            onTabSelected = navigateToTab
        )
        Screen.SETTINGS -> SettingsScreen(
            onTabSelected = navigateToTab,
            onLogout = { backStack = listOf(Screen.SPLASH) }
        )
        else -> PlaceholderScreen(currentScreen = currentScreen, onTabSelected = navigateToTab)
    }
}

@Composable
private fun PlaceholderScreen(currentScreen: Screen, onTabSelected: (Screen) -> Unit) {
    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            BottomNavBar(currentScreen = currentScreen, onTabSelected = onTabSelected)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Próximamente", color = AppText)
        }
    }
}