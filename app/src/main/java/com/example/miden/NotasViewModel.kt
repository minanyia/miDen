package com.example.miden

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotasViewModel(application: Application) : AndroidViewModel(application) {
    private val notaDao = AppDatabase.getDatabase(application).notaDao()

    private val _notas = MutableStateFlow<List<Nota>>(emptyList())
    val notas: StateFlow<List<Nota>> = _notas

    init {
        cargarNotas()
    }

    private fun cargarNotas() {
        viewModelScope.launch {
            notaDao.obtenerTodasLasNotas().collect { listaNotas ->
                _notas.value = listaNotas
            }
        }
    }

    fun agregarNota(titulo: String, descripcion: String) {
        viewModelScope.launch {
            notaDao.insertar(Nota(titulo = titulo, descripcion = descripcion))
        }
    }

    fun actualizarNota(nota: Nota) {
        viewModelScope.launch {
            notaDao.actualizar(nota)
        }
    }

    fun eliminarNota(nota: Nota) {
        viewModelScope.launch {
            notaDao.eliminar(nota)
        }
    }
}