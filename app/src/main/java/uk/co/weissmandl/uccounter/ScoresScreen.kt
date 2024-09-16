package uk.co.weissmandl.uccounter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.animation.AnimatedVisibility

@Composable
fun ScoreScreen(savedScores: List<MainActivity.Score>) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
    ) {
        Scores(scores = savedScores)
    }
}
//@Composable
//fun ScoreCard(score: MainActivity.Score) {
//    Text (text = "${score.total}")
//    Text (text = "Starer Questions Total: ${score.starterCount}")
//    Text (text = "Bonus Questions Total: ${score.bonusCount}")
//}

//@Composable
//fun ScoreCard(score: MainActivity.Score) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
////        elevation = 4.dp,
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(
//                    text = "Total: ${score.total}",
//                    style = MaterialTheme.typography.headlineLarge, // Bigger, bolder text for main info
//                    modifier = Modifier.padding(bottom = 4.dp)
//                )
//                Text(
//                    text = "Starters: ${score.starterCount}",
//                    style = MaterialTheme.typography.bodySmall
//                )
//                Text(
//                    text = "Bonus: ${score.bonusCount}",
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//            // Placeholder for an icon, like a dropdown or delete button
//            Icon(
//                imageVector = Icons.Default.KeyboardArrowDown, // Dropdown icon
//                contentDescription = "Expand",
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
//}

@Composable
fun ScoreCard(score: MainActivity.Score) {
    var expanded by remember { mutableStateOf(false) } // To toggle dropdown visibility

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
//        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: ${score.total}",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f) // Push the icon to the right
                )
                IconButton(
                    onClick = { expanded = !expanded } // Toggle visibility on click
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }

            // Animated visibility to show or hide the extra details
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = "Starters: ${score.starterCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Bonus: ${score.bonusCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun Scores(scores: List<MainActivity.Score>) {
    LazyColumn {
        items(items = scores) { score ->
            ScoreCard(score = score)
        }}
}