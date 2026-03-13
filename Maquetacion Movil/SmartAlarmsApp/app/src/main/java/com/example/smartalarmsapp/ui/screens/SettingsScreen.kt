package com.example.smartalarmsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.res.painterResource
import com.example.smartalarmsapp.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.smartalarmsapp.ui.components.BottomNavBar
import com.example.smartalarmsapp.ui.navigation.Screen
import com.example.smartalarmsapp.ui.theme.AppBackground
import com.example.smartalarmsapp.ui.theme.AppGreen
import com.example.smartalarmsapp.ui.theme.AppPrimary
import com.example.smartalarmsapp.ui.theme.AppRed
import com.example.smartalarmsapp.ui.theme.AppSurface
import com.example.smartalarmsapp.ui.theme.AppSurfaceVariant
import com.example.smartalarmsapp.ui.theme.AppText
import com.example.smartalarmsapp.ui.theme.AppTextSecondary
import com.example.smartalarmsapp.ui.theme.AppToggleTrackOff
import com.example.smartalarmsapp.ui.theme.AppToggleTrackOn
import com.example.smartalarmsapp.ui.theme.SmartAlarmsAppTheme

private data class StoragePlan(
    val label: String,
    val price: String,
    val isCurrent: Boolean = false
)

private val storagePlans = listOf(
    StoragePlan("500GB", "\$50.000\n/Mes", isCurrent = true),
    StoragePlan("1TB", "\$100.000/\nMes"),
    StoragePlan("2TB", "\$150.000/\nMes")
)

@Composable
fun SettingsScreen(onTabSelected: (Screen) -> Unit, onLogout: () -> Unit = {}) {
    var googleCalendar by remember { mutableStateOf(true) }
    var office365 by remember { mutableStateOf(true) }
    var showStorageModal by remember { mutableStateOf(false) }

    if (showStorageModal) {
        StorageUpgradeModal(onDismiss = { showStorageModal = false })
    }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            BottomNavBar(currentScreen = Screen.SETTINGS, onTabSelected = onTabSelected)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                text = "Ajustes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = AppText
            )

            Spacer(Modifier.height(20.dp))

            // ── Avatar ───────────────────────────────────────────────────
            androidx.compose.foundation.Image(
                painter = painterResource(R.drawable.user_avatar),
                contentDescription = null,
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Leonardo Facci",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppText
            )

            Spacer(Modifier.height(24.dp))

            // ── Connectivity ─────────────────────────────────────────────
            SectionLabel("Conectividad")
            Spacer(Modifier.height(8.dp))

            ConnectivityToggleRow(
                iconRes = R.drawable.ic_google_calendar,
                label = "Conectar con Google calendar",
                checked = googleCalendar,
                onCheckedChange = { googleCalendar = it }
            )
            Spacer(Modifier.height(8.dp))
            ConnectivityToggleRow(
                iconRes = R.drawable.ic_office_365,
                label = "Conectar con Office 365",
                checked = office365,
                onCheckedChange = { office365 = it }
            )

            Spacer(Modifier.height(20.dp))

            // ── Cloud storage ─────────────────────────────────────────────
            SectionLabel("Almacenamiento en la Nube")
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cloud,
                            contentDescription = null,
                            tint = AppTextSecondary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Almacenamiento en uso",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppText
                            )
                            Spacer(Modifier.height(6.dp))
                            Button(
                                onClick = { showStorageModal = true },
                                colors = ButtonDefaults.buttonColors(containerColor = AppPrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Administrar", fontSize = 12.sp)
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "350GB/500GB en uso",
                        fontSize = 12.sp,
                        color = AppTextSecondary,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Spacer(Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { 0.70f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AppGreen,
                        trackColor = AppSurfaceVariant,
                        strokeCap = StrokeCap.Round
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Legal links ───────────────────────────────────────────────
            LegalRow("Términos de servicio")
            Spacer(Modifier.height(8.dp))
            LegalRow("Política de privacidad")

            Spacer(Modifier.height(24.dp))

            // ── Logout ────────────────────────────────────────────────────
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppRed),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cerrar Sesión", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ── Storage upgrade modal ──────────────────────────────────────────────────────

@Composable
private fun StorageUpgradeModal(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AppSurface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Incrementa tu capacidad",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppText,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = onDismiss) {
                        Text("✕", color = AppTextSecondary, fontSize = 16.sp)
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    storagePlans.forEach { plan ->
                        StoragePlanTile(plan = plan, modifier = Modifier.weight(1f))
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AppPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Ir a la página del store", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun StoragePlanTile(plan: StoragePlan, modifier: Modifier = Modifier) {
    val bg = if (plan.isCurrent) AppSurfaceVariant else Color(0xFFF59E0B)
    val textColor = if (plan.isCurrent) AppTextSecondary else Color.White

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .then(
                if (plan.isCurrent) Modifier.border(
                    1.dp, AppTextSecondary, RoundedCornerShape(12.dp)
                ) else Modifier
            )
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = plan.label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            if (plan.isCurrent) {
                Text("(actual)", fontSize = 10.sp, color = AppTextSecondary)
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = plan.price,
                fontSize = 11.sp,
                color = textColor,
                lineHeight = 14.sp
            )
        }
    }
}

// ── Reusable sub-composables ──────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        color = AppTextSecondary,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ConnectivityToggleRow(
    icon: ImageVector,
    iconTint: Color,
    iconBackground: Color,
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
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
            }
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
private fun ConnectivityToggleRow(
    @DrawableRes iconRes: Int,
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
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
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
private fun LegalRow(label: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 14.sp, color = AppText)
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = AppTextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SmartAlarmsAppTheme { SettingsScreen(onTabSelected = {}) }
}
