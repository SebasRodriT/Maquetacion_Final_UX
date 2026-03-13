package com.example.smartalarmsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartalarmsapp.ui.components.BottomNavBar
import com.example.smartalarmsapp.ui.navigation.Screen
import com.example.smartalarmsapp.ui.theme.AppBackground
import com.example.smartalarmsapp.ui.theme.AppGreen
import com.example.smartalarmsapp.ui.theme.AppOrange
import com.example.smartalarmsapp.ui.theme.AppPrimary
import com.example.smartalarmsapp.ui.theme.AppSurface
import com.example.smartalarmsapp.ui.theme.AppSurfaceVariant
import com.example.smartalarmsapp.ui.theme.AppText
import com.example.smartalarmsapp.ui.theme.AppTextHint
import com.example.smartalarmsapp.ui.theme.AppTextSecondary
import com.example.smartalarmsapp.ui.theme.AppToggleTrackOff
import com.example.smartalarmsapp.ui.theme.AppToggleTrackOn

// ── Data models ────────────────────────────────────────────────────────────────

private data class AlarmTag(val label: String, val icon: ImageVector)

private data class AlarmItem(
    val id: Int,
    val time: String,
    val name: String,
    val tags: List<AlarmTag>,
    val enabled: Boolean,
    val hasVideo: Boolean = false
)

private val sampleActiveAlarms = listOf(
    AlarmItem(
        id = 1,
        time = "07:00 AM",
        name = "Cocinar desayuno",
        tags = listOf(AlarmTag("Salud", Icons.Filled.Favorite)),
        enabled = true
    ),
    AlarmItem(
        id = 2,
        time = "09:30 AM",
        name = "Reunión con clientes",
        tags = listOf(
            AlarmTag("Trabajo", Icons.Outlined.Work),
            AlarmTag("Urgente", Icons.Outlined.Alarm)
        ),
        enabled = true,
        hasVideo = true
    )
)

private val sampleInactiveAlarms = listOf(
    AlarmItem(
        id = 3,
        time = "09:30 AM",
        name = "Prepararse para clase de yoga",
        tags = listOf(
            AlarmTag("Salud", Icons.Filled.Favorite),
            AlarmTag("Urgente", Icons.Outlined.Alarm)
        ),
        enabled = false
    )
)

private val filterLabels = listOf("Todas", "Trabajo", "Salud", "Personal")

// ── Screen ─────────────────────────────────────────────────────────────────────

@Composable
fun AlarmsScreen(
    currentScreen: Screen,
    onCreateAlarm: () -> Unit,
    onEditAlarm: () -> Unit,
    onTabSelected: (Screen) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Todas") }
    val toggleStates = remember { mutableStateMapOf(1 to true, 2 to true, 3 to false) }

    val filteredActive = remember(selectedFilter) {
        if (selectedFilter == "Todas") sampleActiveAlarms
        else sampleActiveAlarms.filter { alarm ->
            alarm.tags.any { it.label.equals(selectedFilter, ignoreCase = true) }
        }
    }
    val filteredInactive = remember(selectedFilter) {
        if (selectedFilter == "Todas") sampleInactiveAlarms
        else sampleInactiveAlarms.filter { alarm ->
            alarm.tags.any { it.label.equals(selectedFilter, ignoreCase = true) }
        }
    }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            BottomNavBar(currentScreen = currentScreen, onTabSelected = onTabSelected)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateAlarm,
                containerColor = AppPrimary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Crear alarma",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // ── Header ──────────────────────────────────────────────────
            item {
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alertas",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppText
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Cloud,
                            contentDescription = null,
                            tint = AppTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Synced", fontSize = 12.sp, color = AppTextSecondary)
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            // ── Search bar ──────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppSurfaceVariant)
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = AppTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Buscar", fontSize = 14.sp, color = AppTextHint)
                }
                Spacer(Modifier.height(12.dp))
            }

            // ── Filter chips ────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filterLabels.forEach { label ->
                        val selected = label == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (selected) AppGreen else AppSurfaceVariant)
                                .clickable { selectedFilter = label }
                                .padding(horizontal = 16.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = label,
                                fontSize = 13.sp,
                                color = AppText,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // ── Active alarms ───────────────────────────────────────────
            if (filteredActive.isNotEmpty()) {
                item {
                    Text(
                        text = "Alarmas activas",
                        fontSize = 14.sp,
                        color = AppText,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(8.dp))
                }
                items(filteredActive) { alarm ->
                    AlarmCard(
                        alarm = alarm,
                        isEnabled = toggleStates[alarm.id] ?: alarm.enabled,
                        onToggle = { toggleStates[alarm.id] = it },
                        onClick = onEditAlarm
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }

            // ── Inactive alarms ─────────────────────────────────────────
            if (filteredInactive.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Alarmas inactivas",
                        fontSize = 14.sp,
                        color = AppText,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(8.dp))
                }
                items(filteredInactive) { alarm ->
                    AlarmCard(
                        alarm = alarm,
                        isEnabled = toggleStates[alarm.id] ?: alarm.enabled,
                        onToggle = { toggleStates[alarm.id] = it },
                        onClick = onEditAlarm
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

// ── Alarm card ──────────────────────────────────────────────────────────────

@Composable
private fun AlarmCard(
    alarm: AlarmItem,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alarm.time,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppText
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = alarm.name,
                    fontSize = 13.sp,
                    color = AppTextSecondary
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    alarm.tags.forEach { tag -> AlarmTagChip(tag) }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Switch(
                    checked = isEnabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = AppToggleTrackOn,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = AppToggleTrackOff,
                        uncheckedBorderColor = AppToggleTrackOff
                    )
                )
                if (alarm.hasVideo) {
                    Spacer(Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppSurfaceVariant)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Videocam,
                            contentDescription = "Tiene video",
                            tint = AppTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

// ── Tag chip ─────────────────────────────────────────────────────────────────

@Composable
private fun AlarmTagChip(tag: AlarmTag) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(AppOrange)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = tag.icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = tag.label,
            fontSize = 11.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}
