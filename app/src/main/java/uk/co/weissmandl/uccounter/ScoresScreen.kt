package uk.co.weissmandl.uccounter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoreScreen(savedScores: List<MainActivity.Score>) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
    ) {
        Scores(scores = savedScores)
    }
}
@Composable
fun ScoreCard(score: MainActivity.Score) {
    Text (text = "${score.total}")
    Text (text = "${score.starterCount}")
    Text (text = "${score.bonusCount}")
}
@Composable
fun Scores(scores: List<MainActivity.Score>) {
    LazyColumn {
        items(items = scores) { score ->
            ScoreCard(score = score)
        }}
}