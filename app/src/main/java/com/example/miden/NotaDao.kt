package com.example.miden

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas ORDER BY id DESC")
    fun obtenerTodasLasNotas(): Flow<List<Nota>>

    @Insert
    suspend fun insertar(nota: Nota)

    @Update
    suspend fun actualizar(nota: Nota)

    @Delete
    suspend fun eliminar(nota: Nota)

    @Query("DELETE FROM notas")
    suspend fun eliminarTodas()
}