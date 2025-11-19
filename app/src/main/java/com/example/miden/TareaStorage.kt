package com.example.miden

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object TareaStorage {
    private const val FILE_NAME = "tareas.json"

    fun guardarTareas(context: Context, tareas: List<Tarea>) {
        val json = Gson().toJson(tareas)
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun leerTareas(context: Context): MutableList<Tarea> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return mutableListOf()
        val json = file.readText()
        val type = object : TypeToken<MutableList<Tarea>>() {}.type
        return Gson().fromJson(json, type)
    }
}