package uk.co.weissmandl.uccounter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.co.weissmandl.uccounter.models.Score
import uk.co.weissmandl.uccounter.models.ScoreDao
import java.time.LocalDate

class MainViewModel(private val scoreDao: ScoreDao) : ViewModel() {

    private val _starterCount = MutableLiveData(0)
    val starterCount: LiveData<Int> = _starterCount

    private val _bonusCount = MutableLiveData(0)
    val bonusCount: LiveData<Int> = _bonusCount

    private val _total = MutableLiveData(0)
    val total: LiveData<Int> = _total

    private val _savedScores = MutableLiveData<List<Score>>()
    val savedScores: LiveData<List<Score>> = _savedScores

    @RequiresApi(Build.VERSION_CODES.O)
    var selectedDate: LocalDate = LocalDate.now()

    init {
        fetchAllScores()
    }

    fun updateStarterCount(newCount: Int) {
        _starterCount.value = newCount
        recalculateTotal()
    }

    fun updateBonusCount(newCount: Int) {
        _bonusCount.value = newCount
        recalculateTotal()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveScore() {
        viewModelScope.launch {
            val newScore = Score(
                total = _total.value ?: 0,
                starterCount = _starterCount.value ?: 0,
                bonusCount = _bonusCount.value ?: 0,
                date = selectedDate.toString()
            )
            scoreDao.insertScore(newScore)
            fetchAllScores()
            resetCounts()
        }
    }

    fun deleteScore(score: Score) {
        viewModelScope.launch {
            scoreDao.deleteScore(score)
            fetchAllScores()
        }
    }

    private fun fetchAllScores() {
        viewModelScope.launch {
            _savedScores.postValue(scoreDao.getAllScores())
        }
    }

    private fun recalculateTotal() {
        _total.value = (_starterCount.value ?: 0) + (_bonusCount.value ?: 0)
    }

    fun resetCounts() {
        _starterCount.value = 0
        _bonusCount.value = 0
        _total.value = 0
    }
}
