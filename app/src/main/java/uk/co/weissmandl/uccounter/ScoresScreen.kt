package uk.co.weissmandl.uccounter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.weissmandl.uccounter.models.Score

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
@Composable
fun ScoreCard(
    score: Score,
    onDeleteClick: (Score) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var deleteConfirmation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
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
                    text = score.date,
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
