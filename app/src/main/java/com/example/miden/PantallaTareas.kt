package com.example.miden

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun PantallaTareas(navController: NavHostController, viewModel: TareasViewModel = viewModel()) {
    val tareas by viewModel.tareas.collectAsState()
    var nuevaTarea by remember { mutableStateOf("") }
    var nuevaFecha by remember { mutableStateOf("") }
    var tareaEditando by remember { mutableStateOf<Tarea?>(null) }
    var modoEdicion by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text("Mis tareas", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tareas, key = { it.id }) { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(tarea.titulo, fontWeight = FontWeight.Medium)
                            Text(
                                tarea.fecha,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row {
                            Checkbox(
                                checked = tarea.completada,
                                onCheckedChange = { checked ->
                                    viewModel.actualizarTarea(tarea, checked)
                                }
                            )

                            IconButton(onClick = {
                                tareaEditando = tarea
                                nuevaTarea = tarea.titulo
                                nuevaFecha = tarea.fecha
                                modoEdicion = true
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            IconButton(onClick = { viewModel.eliminarTarea(tarea) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (modoEdicion) {
            Text(
                text = "Editando tarea",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = nuevaTarea,
            onValueChange = { nuevaTarea = it },
            label = { Text(if (modoEdicion) "Editar tarea" else "Nueva tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nuevaFecha,
            onValueChange = { nuevaFecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (modoEdicion) {
                OutlinedButton(
                    onClick = {
                        nuevaTarea = ""
                        nuevaFecha = ""
                        modoEdicion = false
                        tareaEditando = null
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }

            Button(
                onClick = {
                    if (nuevaTarea.isNotBlank()) {
                        if (modoEdicion && tareaEditando != null) {
                            viewModel.actualizarTareaCompleta(
                                tareaEditando!!.copy(
                                    titulo = nuevaTarea,
                                    fecha = nuevaFecha
                                )
                            )
                            modoEdicion = false
                            tareaEditando = null
                        } else {
                            viewModel.agregarTarea(nuevaTarea, nuevaFecha)
                        }
                        nuevaTarea = ""
                        nuevaFecha = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (modoEdicion) "Actualizar" else "Agregar")
            }
        }
    }
}

