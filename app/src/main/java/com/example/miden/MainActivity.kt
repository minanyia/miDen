package com.example.miden

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val notasViewModel: NotasViewModel = viewModel()
    val tareasViewModel: TareasViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "notas",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("notas") { PantallaNotas(viewModel = notasViewModel) }
            composable("tareas") { PantallaTareas(navController, viewModel = tareasViewModel) }
            composable("perfil") { PantallaPerfil(navController) }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomItem("notas", "Notas", Icons.Filled.AddCircle),
        BottomItem("tareas", "Tareas", Icons.Filled.CheckCircle),
        BottomItem("perfil", "Perfil", Icons.Filled.Person)
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

data class BottomItem(val route: String, val title: String, val icon: ImageVector)
