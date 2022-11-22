package com.syntax.pokedex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon

@Database(entities = [DatabasePokemon::class], version = 1)
abstract class PokeDatabase: RoomDatabase() {
    abstract val pokeDatabaseDao: PokeDatabaseDao
}

private lateinit var INSTANCE: PokeDatabase

// if there's no Database a new one is built
fun getDatabase(context: Context): PokeDatabase {
    synchronized(PokeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PokeDatabase::class.java,
                "pokemon_database"
            ).allowMainThreadQueries().build()
        }
    }
    return INSTANCE
}