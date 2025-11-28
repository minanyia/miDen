package com.example.miden

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PantallaPerfil(navController: NavHostController) {

    var nombre by remember { mutableStateOf("Romina y Karen") }
    var carrera by remember { mutableStateOf("Estudiantes de aplicaciones moviles") }
    var descripcion by remember { mutableStateOf("nos encanta trabajar en equipo sisisi") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Perfil", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.height(16.dp))
        Text(text = nombre, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Text(text = carrera, fontSize = 16.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            text = descripcion,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(24.dp))
        Button(onClick = { navController.navigate("tareas") }) {
            Text("Ver mis tareas")
        }
    }
}
