package uk.co.weissmandl.uccounter.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: Score)

    @Query("SELECT * FROM scores ORDER BY date DESC")
    suspend fun getAllScores(): List<Score>

    @Delete
    suspend fun deleteScore(score: Score)
}

