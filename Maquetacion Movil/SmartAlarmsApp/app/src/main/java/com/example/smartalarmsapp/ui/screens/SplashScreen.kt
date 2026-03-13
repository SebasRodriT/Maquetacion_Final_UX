package com.example.smartalarmsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartalarmsapp.R
import com.example.smartalarmsapp.ui.theme.AppBackground
import com.example.smartalarmsapp.ui.theme.AppPrimary
import com.example.smartalarmsapp.ui.theme.AppText
import com.example.smartalarmsapp.ui.theme.AppTextSecondary
import com.example.smartalarmsapp.ui.theme.SmartAlarmsAppTheme

@Composable
fun SplashScreen(onStart: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {

        // ── Hero image area ──────────────────────────────────────────────
        Image(
            painter = painterResource(id = R.drawable.splash_hero),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
        )

        // ── Bottom content ───────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Smart Alarms",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = AppText
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppPrimary)
            ) {
                Text(
                    text = "Iniciar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Mantén el control de tu día por medio de alarmas personalizadas",
                fontSize = 14.sp,
                color = AppTextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SmartAlarmsAppTheme {
        SplashScreen()
    }
}
