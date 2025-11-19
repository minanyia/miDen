package com.example.miden

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TareasViewModel : ViewModel() {

    var tareas = mutableStateListOf<Tarea>()
        private set

    fun cargarTareas(context: Context) {
        val cargadas = TareaStorage.leerTareas(context)
        tareas.clear()
        tareas.addAll(cargadas)
    }

    fun actualizarTarea(tarea: Tarea, completada: Boolean) {
        val index = tareas.indexOf(tarea)
        if (index != -1) {
            tareas[index] = tareas[index].copy(completada = completada)
        }
    }

    fun agregarTarea(tarea: Tarea) {
        tareas.add(tarea)
    }
}