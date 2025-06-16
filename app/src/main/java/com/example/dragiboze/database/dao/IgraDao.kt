package com.example.dragiboze.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dragiboze.database.entities.IgraDbModel

@Dao
interface IgraDao {

    @Query("Select * from igre order by vremeKreiranja desc")
    suspend fun getAll(): List<IgraDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(igraDbModel: IgraDbModel)

    @Update
    suspend fun update(igraDbModel: IgraDbModel)

    @Query("Select * from igre order by vremeKreiranja desc limit 1")
    suspend fun getLast(): IgraDbModel

    @Query("Select * from igre where korisnik = :korisnik order by vremeKreiranja desc")
    suspend fun getIgreByKorisnik(korisnik: String): List<IgraDbModel>

}