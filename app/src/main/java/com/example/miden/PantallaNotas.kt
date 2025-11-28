package com.example.miden

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit   // ⭐ FALTA IMPORT
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton    // ⭐ FALTA IMPORT
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PantallaNotas(viewModel: NotasViewModel = viewModel()) {
    val notas by viewModel.notas.collectAsState()
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var notaEditando by remember { mutableStateOf<Nota?>(null) }
    var modoEdicion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Mis Notas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(notas, key = { it.id }) { nota ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(nota.titulo, fontWeight = FontWeight.Bold)
                            Text(nota.descripcion)
                        }
                        Row {
                            // Editar
                            IconButton(onClick = {
                                notaEditando = nota
                                titulo = nota.titulo
                                descripcion = nota.descripcion
                                modoEdicion = true
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            // Eliminar
                            IconButton(onClick = { viewModel.eliminarNota(nota) }) {
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

        Spacer(modifier = Modifier.height(16.dp))

        if (modoEdicion) {
            Text(
                text = "Editando nota",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de la nota") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (modoEdicion) {
                OutlinedButton(
                    onClick = {
                        titulo = ""
                        descripcion = ""
                        modoEdicion = false
                        notaEditando = null
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }

            Button(
                onClick = {
                    if (titulo.isNotBlank() && descripcion.isNotBlank()) {
                        if (modoEdicion && notaEditando != null) {
                            viewModel.actualizarNota(
                                notaEditando!!.copy(
                                    titulo = titulo,
                                    descripcion = descripcion
                                )
                            )
                            modoEdicion = false
                            notaEditando = null
                        } else {
                            viewModel.agregarNota(titulo, descripcion)
                        }
                        titulo = ""
                        descripcion = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (modoEdicion) "Actualizar" else "Agregar")
            }
        }
    }
}
