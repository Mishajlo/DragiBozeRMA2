package com.example.dragiboze.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dragiboze.database.entities.TakmicarDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TakmicarDao {

    @Query("Select * from takmicari order by rezultat desc")
    suspend fun getAll(): List<TakmicarDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(takmicarDbModel: TakmicarDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listTakmicara: List<TakmicarDbModel>)

    @Query("Select rezultat from takmicari where korisnik = :korisnik order by rezultat desc limit 1")
    suspend fun getBestForKorisnik(korisnik: String): Pair<Int,Double>{
        val svi = getAll()
        val poKorisniku = svi.find { it.korisnik == korisnik }
        val poz = svi.indexOf(poKorisniku) + 1
        poKorisniku?.rezultat?.let {
            return Pair(poz, poKorisniku.rezultat)
        }
        return Pair(poz, 0.0)
    }

    @Query("Select * from takmicari order by rezultat desc limit 10")
    suspend fun getTopTen(): List<TakmicarDbModel>

    @Query("Delete from takmicari")
    suspend fun deleteAll()

    @Query("Select * from takmicari order by rezultat desc")
    fun observeAll(): Flow<List<TakmicarDbModel>>

}