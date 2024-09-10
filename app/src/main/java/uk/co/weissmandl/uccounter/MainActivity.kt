package uk.co.weissmandl.uccounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.weissmandl.uccounter.ui.theme.UCCounterTheme

class MainActivity : ComponentActivity() {
    data class Score(val total: Int, val starterCount: Int, val bonusCount: Int)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UCCounterTheme {
                var starterCount by remember { mutableStateOf(0) }
                var bonusCount by remember { mutableStateOf(0) }
                var total by remember { mutableStateOf(0) }
                val helveticaneue = FontFamily(
                    Font(R.font.helveticaneue, FontWeight.Bold)
                )
                var savedScores by remember { mutableStateOf(listOf<Score>()) }

                total = starterCount + bonusCount

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Banner(fontFamily = helveticaneue, total = "$total")
                    Spacer(modifier = Modifier.height(50.dp))

                    Text(
                        text = "$total",
                        fontSize = 70.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(50.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Question("Starter Question", 10, starterCount, onCountChange = { newCount ->
                            starterCount = newCount
                        })
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Question("Bonus Question", 5, bonusCount, onCountChange = {newCount ->
                            bonusCount = newCount
                        })
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        homePageButton(
                            onClick = { val newScore = Score(total, starterCount, bonusCount)
                                      savedScores = savedScores + newScore
                                    starterCount = 0
                                    bonusCount = 0
                                    total = 0
                                      },
                            text = "Save"
                        )}
                    Spacer(modifier = Modifier.weight(1f))
                    ScoreScreen(savedScores = savedScores)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        homePageButton(
                            onClick = { bonusCount -= 10 },
                            text = "-10"
                        )
                        homePageButton(
                            onClick = { bonusCount -= 5 },
                            text = "-5"
                        )
                        homePageButton(
                            onClick = {
                                starterCount = 0
                                bonusCount = 0
                                total = 0 },
                            text = "Reset"
                        )
                        }
                    }
                }
            }
    }
}

@Composable
fun Question(title: String, questionType: Int, count: Int, onCountChange: (Int) -> Unit) {
    homePageButton(
        onClick = {
            val newCount = count + questionType
            onCountChange(newCount) },
        text = "$title   +$questionType"
    )
    Text(
        text = "$count",
        fontSize = 30.sp,
    )
}

@Composable
fun homePageButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color(0xFF2f00f6)),
        shape = RectangleShape
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}

@Composable
fun Banner(fontFamily: FontFamily, total: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxWidth()
            .padding(2.dp)
            .height(58.dp)
    ) {
        Text (
            text = " UC ",
            color = Color.White,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = Color(0xFF2f00f6))
                .height(50.dp)
                .width(50.dp)
                .wrapContentHeight()
        )
        Text (
            text=" University Challenge ",
            color = Color.Black,
            fontFamily = fontFamily,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = Color(0xFFfbff9d))
                .weight(1f)
                .height(45.dp)
                .wrapContentHeight()
        )
        Text (
            text=" $total ",
            color = Color.White,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = Color(0xFF2f00f6))
                .height(50.dp)
                .width(50.dp)
                .wrapContentHeight()
        )
    }
}
//@Composable
//fun ScoreCard(score: MainActivity.Score) {
//        Text (text = "${score.total}")
//        Text (text = "${score.starterCount}")
//        Text (text = "${score.bonusCount}")
//}
//@Composable
//fun Scores(scores: List<MainActivity.Score>) {
//    LazyColumn {
//        items(items = scores) { score ->
//            ScoreCard(score = score)
//}}}
//    LazyColumn { items(items = scores) { score -> ScoreCard(score) }}}

//Text("Number of scores: ${scores.size}") THIS WILL GIVE ME TOTAL AMOUNT OF SCORES ;)