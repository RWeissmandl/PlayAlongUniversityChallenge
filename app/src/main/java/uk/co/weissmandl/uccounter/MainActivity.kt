package uk.co.weissmandl.uccounter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import uk.co.weissmandl.uccounter.models.MainViewModelFactory
import uk.co.weissmandl.uccounter.models.ScoreDatabase
import uk.co.weissmandl.uccounter.ui.theme.UCCounterTheme

class MainActivity : ComponentActivity() {

    object DatabaseBuilder {
        private var INSTANCE: ScoreDatabase? = null

        fun getInstance(context: Context): ScoreDatabase {
            if (INSTANCE == null) {
                synchronized(ScoreDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScoreDatabase::class.java,
                        "score_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseBuilder.getInstance(applicationContext)
        val scoreDao = database.scoreDao()
        val viewModelFactory = MainViewModelFactory(scoreDao)

        setContent {
            UCCounterTheme {
                val viewModel: MainViewModel by viewModels{ viewModelFactory }
                val navController = rememberNavController()
                val totalState = viewModel.total.observeAsState(0)
                val total = totalState.value.toString()
                val helveticaneue = FontFamily(Font(R.font.helveticaneue, FontWeight.Bold))
                @OptIn(ExperimentalMaterial3Api::class)
                Scaffold(
                    topBar = { Banner(fontFamily = helveticaneue, total = "$total") },
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { contentPadding ->
                    Box(modifier = Modifier
                        .padding(contentPadding)
                        .padding(10.dp)
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                HomeScreen(viewModel = viewModel)
                            }
                            composable("scores") {
                                ScoreScreen(
                                    viewModel = viewModel,
                                    savedScores = viewModel.savedScores.observeAsState(emptyList()).value
                                )
                            }
                            composable("averages") {
                                AveragesScreen(
                                    savedScores = viewModel.savedScores.observeAsState(emptyList()).value
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}