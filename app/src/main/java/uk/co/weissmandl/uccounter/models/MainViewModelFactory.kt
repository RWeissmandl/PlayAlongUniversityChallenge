package uk.co.weissmandl.uccounter.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.co.weissmandl.uccounter.MainViewModel

class MainViewModelFactory(private val scoreDao: ScoreDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(scoreDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
