package uk.co.weissmandl.uccounter.models

import androidx.room.*
@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: Score)

    @Query("SELECT * FROM scores")
    suspend fun getAllScores(): List<Score>

    @Delete
    suspend fun deleteScore(score: Score)
}

