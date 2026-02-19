package com.example.viikkoteht1.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity = tämä data class vastaa yhtä tietokannan taulua
// tableName = taulun nimi SQLite-tietokannassa
@Entity(tableName = "tasks")
data class Task(
    // @PrimaryKey = uniikki tunniste jokaiselle riville
    // autoGenerate = true → Room antaa id:n automaattisesti (1, 2, 3, ...)
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // @ColumnInfo = sarakkeen nimi tietokannassa
    // Kotlin: "title" → SQLite-sarake: "title"
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    // Oletusarvo false → uusi rivi saa automaattisesti arvon false
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,

    // System.currentTimeMillis() = nykyhetki millisekunteina
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "due_date")
    val dueDate: String
)