package com.example.miden

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.miden.TareasViewModel
import com.example.miden.Tarea
import com.example.miden.TareaStorage

@Composable
fun PantallaTareas(navController: NavHostController) {

    val context = LocalContext.current
    val viewModel: TareasViewModel = viewModel()

    // Cargar tareas solo 1 vez
    LaunchedEffect(Unit) {
        viewModel.cargarTareas(context)
    }

    var nuevaTarea by remember { mutableStateOf("") }
    var nuevaFecha by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {

        Text("Mis tareas", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        // LISTA CORRECTA
        LazyColumn {
            items(viewModel.tareas) { tarea ->

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(tarea.titulo)
                        Text(tarea.fecha, fontSize = 12.sp)
                    }

                    Checkbox(
                        checked = tarea.completada,
                        onCheckedChange = { checked ->
                            viewModel.actualizarTarea(tarea, checked)
                            TareaStorage.guardarTareas(context, viewModel.tareas)
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nuevaTarea,
            onValueChange = { nuevaTarea = it },
            label = { Text("Nueva tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nuevaFecha,
            onValueChange = { nuevaFecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nuevaTarea.isNotBlank()) {
                    val nueva = Tarea(nuevaTarea, nuevaFecha)
                    viewModel.agregarTarea(nueva)
                    TareaStorage.guardarTareas(context, viewModel.tareas)

                    nuevaTarea = ""
                    nuevaFecha = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar tarea")
        }
    }
}
