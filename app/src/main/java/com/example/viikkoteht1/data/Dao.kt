package com.example.viikkoteht1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// @Dao = Data Access Object, määrittelee tietokantaoperaatiot
// Room generoi toteutuksen automaattisesti tämän interfacen pohjalta
@Dao
interface TaskDao {

    // ── CREATE (lisääminen) ──────────────────────────────
    // suspend = suoritetaan korutiinissa (ei jumita UI:ta)
    // onConflict = REPLACE → jos sama id löytyy, korvaa vanha rivi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long  // Palauttaa lisätyn rivin id:n

    // vararg = voi antaa monta Task-oliota kerralla
    @Insert
    suspend fun insertAll(vararg tasks: Task)

    // ── READ (lukeminen) ─────────────────────────────────
    // Flow<List<Task>> = reaktiivinen virta
    // → kun taulun data muuttuu, Flow lähettää uuden listan automaattisesti
    // → UI päivittyy ilman erillistä hakua!
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    // :taskId = parametri, joka tulee funktion argumentista
    // Task? = voi palauttaa null jos riviä ei löydy
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Query("SELECT * FROM tasks WHERE is_completed = :completed")
    fun getTasksByStatus(completed: Boolean): Flow<List<Task>>

    // LIKE '%hakusana%' = etsi osittainen vastaavuus mistä tahansa kohdasta
    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :searchQuery || '%'")
    fun searchTasks(searchQuery: String): Flow<List<Task>>

    // ── UPDATE (päivittäminen) ───────────────────────────
    // @Update päivittää koko rivin pääavaimen (id) perusteella
    @Update
    suspend fun update(task: Task)

    // Voi myös päivittää yksittäisen sarakkeen SQL:llä
    @Query("UPDATE tasks SET is_completed = :completed WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Int, completed: Boolean)



    // ── DELETE (poistaminen) ─────────────────────────────
    // @Delete poistaa rivin pääavaimen perusteella
    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteById(taskId: Int)

    // is_completed = 1 → SQLite tallentaa Booleanin numerona (0/1)
    @Query("DELETE FROM tasks WHERE is_completed = 1")
    suspend fun deleteCompletedTasks()

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    // ── TILASTOT ─────────────────────────────────────────
    // COUNT(*) = laske rivien lukumäärä
    @Query("SELECT COUNT(*) FROM tasks")
    fun getTaskCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE is_completed = 0")
    fun getPendingTaskCount(): Flow<Int>
}