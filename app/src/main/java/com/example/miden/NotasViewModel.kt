package com.example.miden

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class NotasViewModel : ViewModel() {
    val notas = mutableStateListOf<Nota>()

    fun agregarNota(titulo: String, descripcion: String) {
        notas.add(Nota(titulo, descripcion))
    }
}