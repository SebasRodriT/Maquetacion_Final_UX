package com.example.smartalarmsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.EventBusy
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.smartalarmsapp.ui.theme.AppGreen
import com.example.smartalarmsapp.ui.theme.AppPrimary
import com.example.smartalarmsapp.ui.theme.AppRed
import com.example.smartalarmsapp.ui.theme.AppSurface
import com.example.smartalarmsapp.ui.theme.AppSurfaceVariant
import com.example.smartalarmsapp.ui.theme.AppText
import com.example.smartalarmsapp.ui.theme.AppTextHint
import com.example.smartalarmsapp.ui.theme.AppTextSecondary
import com.example.smartalarmsapp.ui.theme.AppToggleTrackOff
import com.example.smartalarmsapp.ui.theme.AppToggleTrackOn
import com.example.smartalarmsapp.ui.theme.SmartAlarmsAppTheme

private enum class DismissMethod { EASY, MEDIUM, HARD }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateAlarmScreen(
    isEditMode: Boolean = false,
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {},
    onTabSelected: (Screen) -> Unit = {}
) {
    val timePickerState = rememberTimePickerState(initialHour = 8, initialMinute = 0, is24Hour = false)

    var alarmName by remember { mutableStateOf("") }
    var hasVideo by remember { mutableStateOf(false) }

    val dayLabels = listOf("L", "M", "M", "J", "V", "S", "D")
    val selectedDays = remember { mutableStateListOf(2, 5) }

    val availableTags = remember { mutableStateListOf("Personal", "Descanso") }
    val selectedTags = remember { mutableStateListOf("Todas") }

    var dismissMethod by remember { mutableStateOf(DismissMethod.MEDIUM) }
    var syncCalendar by remember { mutableStateOf(true) }
    var disableOnHolidays by remember { mutableStateOf(false) }
    var alarmActive by remember { mutableStateOf(true) }
    var shareAlarm by remember { mutableStateOf(false) }

    var showCreateTagDialog by remember { mutableStateOf(false) }
    var newTagText by remember { mutableStateOf("") }

    // ── Create tag dialog ─────────────────────────────────────────────────────
    if (showCreateTagDialog) {
        AlertDialog(
            onDismissRequest = { showCreateTagDialog = false },
            title = { Text("Nueva etiqueta", color = AppText) },
            text = {
                OutlinedTextField(
                    value = newTagText,
                    onValueChange = { newTagText = it },
                    placeholder = { Text("Nombre de la etiqueta", color = AppTextHint) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = AppSurfaceVariant,
                        focusedContainerColor = AppSurfaceVariant,
                        focusedBorderColor = AppPrimary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = AppText,
                        unfocusedTextColor = AppText
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newTagText.isNotBlank()) availableTags.add(newTagText.trim())
                    newTagText = ""
                    showCreateTagDialog = false
                }) { Text("Crear", color = AppPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { newTagText = ""; showCreateTagDialog = false }) {
                    Text("Cancelar", color = AppTextSecondary)
                }
            },
            containerColor = AppSurface
        )
    }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(containerColor = AppRed),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Filled.Close, null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Cancelar", fontSize = 13.sp)
                }
                Text(
                    text = if (isEditMode) "Editar alarma" else "Crear alarma",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = AppText
                )
                Button(
                    onClick = onSave,
                    colors = ButtonDefaults.buttonColors(containerColor = AppPrimary),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Outlined.Check, null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Guardar", fontSize = 13.sp)
                }
            }
        },
        bottomBar = {
            BottomNavBar(currentScreen = Screen.CREATE_ALARM, onTabSelected = onTabSelected)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            // ── Time picker ──────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TimeInput(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            timeSelectorSelectedContainerColor = AppSurfaceVariant,
                            timeSelectorUnselectedContainerColor = AppSurfaceVariant,
                            timeSelectorSelectedContentColor = AppPrimary,
                            timeSelectorUnselectedContentColor = AppText,
                            periodSelectorBorderColor = Color(0xFFFFB3BA),
                            periodSelectorSelectedContainerColor = Color(0xFFFFB3BA).copy(alpha = 0.25f),
                            periodSelectorUnselectedContainerColor = Color.Transparent,
                            periodSelectorSelectedContentColor = AppText,
                            periodSelectorUnselectedContentColor = AppTextSecondary
                        )
                    )
                }
            }

            // ── Alarm name ───────────────────────────────────────────────
            Text("Ingrese un nombre para su alarma:", fontSize = 14.sp, color = AppText)
            OutlinedTextField(
                value = alarmName,
                onValueChange = { alarmName = it },
                placeholder = { Text("Nombre de la alarma", color = AppTextHint) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = AppSurfaceVariant,
                    focusedContainerColor = AppSurfaceVariant,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = AppPrimary,
                    unfocusedTextColor = AppText,
                    focusedTextColor = AppText,
                    cursorColor = AppPrimary
                )
            )

            // ── Video reminder ───────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Recordatorio de video", fontSize = 14.sp, color = AppText)
                if (!hasVideo) {
                    Button(
                        onClick = { hasVideo = true },
                        colors = ButtonDefaults.buttonColors(containerColor = AppPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Outlined.Videocam, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Añadir video")
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1A1A2E)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.PlayCircle,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.size(56.dp)
                        )
                        Text(
                            "Vista previa del video",
                            color = AppTextSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp)
                        )
                    }
                    Button(
                        onClick = { hasVideo = false },
                        colors = ButtonDefaults.buttonColors(containerColor = AppPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Outlined.Videocam, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Reemplazar video")
                    }
                }
            }

            // ── Repeat days ──────────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Repetir los días", fontSize = 14.sp, color = AppText)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    dayLabels.forEachIndexed { index, label ->
                        val isSelected = index in selectedDays
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) AppGreen else Color.White)
                                .clickable {
                                    if (isSelected) selectedDays.remove(index)
                                    else selectedDays.add(index)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF333333)
                            )
                        }
                    }
                }
            }

            // ── Tags ─────────────────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Añadir etiquetas", fontSize = 14.sp, color = AppText)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // "Salud" is always shown first as a fixed tag
                    TagChip(label = "Salud", selected = "Salud" in selectedTags) {
                        selectedTags.remove("Todas")
                        if ("Salud" in selectedTags) selectedTags.remove("Salud")
                        else selectedTags.add("Salud")
                    }
                    TagChip(label = "Todas", selected = "Todas" in selectedTags) {
                        selectedTags.clear()
                        selectedTags.add("Todas")
                    }
                    availableTags.forEach { tag ->
                        TagChip(label = tag, selected = tag in selectedTags) {
                            selectedTags.remove("Todas")
                            if (tag in selectedTags) selectedTags.remove(tag)
                            else selectedTags.add(tag)
                        }
                    }
                }
                Button(
                    onClick = { showCreateTagDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AppPrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Icon(Icons.Filled.Add, null, modifier = Modifier.size(16.dp), tint = Color.White)
                    Spacer(Modifier.width(4.dp))
                    Text("Crear etiqueta", color = Color.White, fontSize = 14.sp)
                }
            }

            // ── Dismiss method ───────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("Método para apagar la alarma:", fontSize = 14.sp, color = AppText)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DismissOption(
                            icon = Icons.Outlined.SentimentSatisfied,
                            label = "Fácil",
                            selected = dismissMethod == DismissMethod.EASY,
                            onClick = { dismissMethod = DismissMethod.EASY }
                        )
                        DismissOption(
                            icon = Icons.Outlined.GridView,
                            label = "Medio",
                            selected = dismissMethod == DismissMethod.MEDIUM,
                            onClick = { dismissMethod = DismissMethod.MEDIUM }
                        )
                        DismissOption(
                            icon = Icons.Outlined.SentimentDissatisfied,
                            label = "Difícil",
                            selected = dismissMethod == DismissMethod.HARD,
                            onClick = { dismissMethod = DismissMethod.HARD }
                        )
                    }
                }
            }

            // ── Toggles ──────────────────────────────────────────────────
            ToggleRow(
                icon = Icons.Outlined.CalendarToday,
                label = "Sincronizar con Google calendar",
                checked = syncCalendar,
                onCheckedChange = { syncCalendar = it }
            )
            ToggleRow(
                icon = Icons.Outlined.EventBusy,
                label = "Desactivar en festivos",
                checked = disableOnHolidays,
                onCheckedChange = { disableOnHolidays = it }
            )
            ToggleRow(
                icon = Icons.Outlined.Alarm,
                label = "Activar alarma",
                checked = alarmActive,
                onCheckedChange = { alarmActive = it }
            )

            ActionRow(
                icon = Icons.Outlined.Share,
                label = "Compartir alarma",
                onClick = {}
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ── Reusable sub-composables ──────────────────────────────────────────────────

@Composable
private fun TagChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) AppGreen else AppSurfaceVariant)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = AppText,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun DismissOption(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) AppGreen else AppSurfaceVariant)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(icon, contentDescription = label, tint = AppText, modifier = Modifier.size(26.dp))
        Text(label, fontSize = 11.sp, color = AppText)
    }
}

@Composable
private fun ToggleRow(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = AppTextSecondary, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, color = AppText, modifier = Modifier.weight(1f))
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = AppToggleTrackOn,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = AppToggleTrackOff,
                    uncheckedBorderColor = AppToggleTrackOff
                )
            )
        }
    }
}

@Composable
private fun ActionRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = AppTextSecondary, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, color = AppText)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateAlarmScreenPreview() {
    SmartAlarmsAppTheme { CreateAlarmScreen() }
}
