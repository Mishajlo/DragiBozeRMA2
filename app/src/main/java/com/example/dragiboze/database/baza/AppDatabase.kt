package com.example.dragiboze.database.baza

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dragiboze.database.dao.IgraDao
import com.example.dragiboze.database.dao.MacaDao
import com.example.dragiboze.database.dao.SlikaDao
import com.example.dragiboze.database.dao.TakmicarDao
import com.example.dragiboze.database.entities.IgraDbModel
import com.example.dragiboze.database.entities.MacaDbModel
import com.example.dragiboze.database.entities.SlikaDbModel
import com.example.dragiboze.database.entities.TakmicarDbModel

@Database(
    entities = [
        MacaDbModel::class,
        SlikaDbModel::class,
        TakmicarDbModel::class,
        IgraDbModel::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun macaDao(): MacaDao

    abstract fun slikaDao(): SlikaDao

    abstract fun takmicarDao(): TakmicarDao

    abstract fun igraDao(): IgraDao

}