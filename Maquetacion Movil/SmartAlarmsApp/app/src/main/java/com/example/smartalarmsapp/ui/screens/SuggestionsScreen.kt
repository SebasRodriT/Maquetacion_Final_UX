package com.example.smartalarmsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartalarmsapp.ui.components.BottomNavBar
import com.example.smartalarmsapp.ui.navigation.Screen
import com.example.smartalarmsapp.ui.theme.AppBackground
import com.example.smartalarmsapp.ui.theme.AppPrimary
import com.example.smartalarmsapp.ui.theme.AppSurface
import com.example.smartalarmsapp.ui.theme.AppText
import com.example.smartalarmsapp.ui.theme.AppTextSecondary
import com.example.smartalarmsapp.ui.theme.SmartAlarmsAppTheme

// ── Data model ────────────────────────────────────────────────────────────────

private data class Suggestion(
    val icon: ImageVector,
    val iconTint: Color,
    val iconBackground: Color,
    val title: String,
    val scheduledAt: String,
    val recommendedAlarm: String
)

private val tomorrowSuggestions = listOf(
    Suggestion(
        icon = Icons.Outlined.Flight,
        iconTint = Color.White,
        iconBackground = Color(0xFF22A06B),
        title = "Vuelo a JFK",
        scheduledAt = "Programado a las 8:00 AM",
        recommendedAlarm = "6:00 AM"
    ),
    Suggestion(
        icon = Icons.Outlined.Sync,
        iconTint = Color.White,
        iconBackground = Color(0xFF1A73E8),
        title = "Reunión de syncronización",
        scheduledAt = "Programado a las 4:00 PM",
        recommendedAlarm = "3:30 PM"
    )
)

private val thisWeekSuggestions = listOf(
    Suggestion(
        icon = Icons.Outlined.MedicalServices,
        iconTint = Color.White,
        iconBackground = Color(0xFFE53935),
        title = "Cita médica",
        scheduledAt = "Programado el 11/10/2025 a las 4:00 PM",
        recommendedAlarm = "3:30 PM"
    )
)

// ── Screen ────────────────────────────────────────────────────────────────────

@Composable
fun SuggestionsScreen(
    onCreateAlarm: () -> Unit,
    onTabSelected: (Screen) -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            BottomNavBar(currentScreen = Screen.SUGGESTIONS, onTabSelected = onTabSelected)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Recomendaciones",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppText
                )
                Spacer(Modifier.height(20.dp))
            }

            // ── Para mañana ──────────────────────────────────────────────
            item {
                SectionHeader("Para mañana")
                Spacer(Modifier.height(10.dp))
            }
            items(tomorrowSuggestions) { suggestion ->
                SuggestionCard(suggestion = suggestion, onCreateAlarm = onCreateAlarm)
                Spacer(Modifier.height(10.dp))
            }

            // ── Esta semana ──────────────────────────────────────────────
            item {
                Spacer(Modifier.height(6.dp))
                SectionHeader("Esta semana")
                Spacer(Modifier.height(10.dp))
            }
            items(thisWeekSuggestions) { suggestion ->
                SuggestionCard(suggestion = suggestion, onCreateAlarm = onCreateAlarm)
                Spacer(Modifier.height(10.dp))
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

// ── Sub-composables ───────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = AppText
    )
}

@Composable
private fun SuggestionCard(suggestion: Suggestion, onCreateAlarm: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon badge
            Icon(
                imageVector = suggestion.icon,
                contentDescription = null,
                tint = suggestion.iconTint,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(suggestion.iconBackground)
                    .padding(10.dp)
            )

            Spacer(Modifier.width(12.dp))

            // Text content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = suggestion.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppText
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = suggestion.scheduledAt,
                    fontSize = 12.sp,
                    color = AppTextSecondary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Alarma recomendada: ${suggestion.recommendedAlarm}",
                    fontSize = 12.sp,
                    color = AppTextSecondary
                )
            }

            Spacer(Modifier.width(10.dp))

            // CTA button
            Button(
                onClick = onCreateAlarm,
                colors = ButtonDefaults.buttonColors(containerColor = AppPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Crear alarma", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SuggestionsScreenPreview() {
    SmartAlarmsAppTheme { SuggestionsScreen(onCreateAlarm = {}, onTabSelected = {}) }
}
