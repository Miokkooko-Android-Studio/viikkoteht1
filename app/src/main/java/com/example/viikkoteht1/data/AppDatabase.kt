package com.example.viikkoteht1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// @Database kertoo Room:ille mitä tauluja tietokanta sisältää
// entities = lista kaikista Entity-luokista (= taulut)
// version = tietokannan versio (kasvatetaan kun rakenne muuttuu)
@Database(
    entities = [Task::class],
    version = 2,
    exportSchema = false  // true tuotannossa → tallentaa skeeman tiedostoon
)
abstract class AppDatabase : RoomDatabase() {
    // Abstraktit funktiot: Room generoi toteutuksen automaattisesti
    // Näiden kautta pääset käsiksi DAO:n metodeihin
    abstract fun taskDao(): TaskDao


    // Singleton-pattern: varmistaa että koko sovelluksessa on
    // VAIN YKSI tietokantayhteys → estää ristiriidat ja lukitukset
    companion object {
        // @Volatile = arvo näkyy heti kaikille säikeille
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Jos instanssi on jo olemassa, palauta se suoraan
            // synchronized = vain yksi säie kerrallaan voi luoda tietokannan
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Application context (elää koko sovelluksen ajan)
                    AppDatabase::class.java,     // Tietokantaluokka
                    "app_database"               // Tiedoston nimi: /databases/app_database
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}