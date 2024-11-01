package uk.co.weissmandl.uccounter.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val total: Int,
    val starterCount: Int,
    val bonusCount: Int,
    val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
)
