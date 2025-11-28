package com.example.miden

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TareasViewModel(application: Application) : AndroidViewModel(application) {
    private val tareaDao = AppDatabase.getDatabase(application).tareaDao()

    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> = _tareas

    init {
        cargarTareas()
    }

    private fun cargarTareas() {
        viewModelScope.launch {
            tareaDao.obtenerTodasLasTareas().collect { listaTareas ->
                _tareas.value = listaTareas
            }
        }
    }

    fun agregarTarea(titulo: String, fecha: String) {
        viewModelScope.launch {
            val nuevaTarea = Tarea(
                id = 0,
                titulo = titulo,
                fecha = fecha,
                completada = false
            )
            tareaDao.insertarTarea(nuevaTarea)
        }
    }

    fun actualizarTarea(tarea: Tarea, completada: Boolean) {
        viewModelScope.launch {
            val tareaActualizada = tarea.copy(completada = completada)
            tareaDao.actualizarTarea(tareaActualizada)
        }
    }

    // Nuevo método para actualizar toda la tarea
    fun actualizarTareaCompleta(tarea: Tarea) {
        viewModelScope.launch {
            tareaDao.actualizarTarea(tarea)
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            tareaDao.eliminarTarea(tarea)
        }
    }
}