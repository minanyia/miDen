package com.example.miden

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Query("SELECT * FROM tareas ORDER BY id DESC")
    fun obtenerTodasLasTareas(): Flow<List<Tarea>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTarea(tarea: Tarea)

    @Update
    suspend fun actualizarTarea(tarea: Tarea)

    @Delete
    suspend fun eliminarTarea(tarea: Tarea)

    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun obtenerTareaPorId(id: Int): Tarea?
}