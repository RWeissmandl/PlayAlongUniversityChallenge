package uk.co.weissmandl.uccounter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.weissmandl.uccounter.models.Score

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScoreScreen(
    viewModel: MainViewModel,
    savedScores: List<Score>
) {
    Scores(
        scores = savedScores,
        onDeleteScore = { score -> viewModel.deleteScore(score)
        }
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScoreCard(
    score: Score,
    onDeleteClick: (Score) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var deleteConfirmation by remember { mutableStateOf(false) }
    val formattedDate = formatDate(score.date)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${score.total}",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "Starters: ${score.starterCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Bonus: ${score.bonusCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(
                        onClick = {deleteConfirmation = true},
                        modifier = Modifier.fillMaxWidth()
                        ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Score"
                        )
                    }
                }
            }
        }
    }
    if (deleteConfirmation) {
        AlertDialog(
            onDismissRequest = { deleteConfirmation = false },
            title = {
                Text(text = "Confirm Deletion")
            },
            text = {
                Text(text = "Are you sure you want to delete this score?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(score)
                        deleteConfirmation = false
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { deleteConfirmation = false }
                ) {
                    Text(text = "No")
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Scores(
    scores: List<Score>,
    onDeleteScore: (Score) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(scores) { score ->
            ScoreCard(score = score, onDeleteClick = onDeleteScore)
        }
    }
}
