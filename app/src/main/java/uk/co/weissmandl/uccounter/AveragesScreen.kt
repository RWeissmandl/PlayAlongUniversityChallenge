package uk.co.weissmandl.uccounter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.weissmandl.uccounter.models.Score

@Composable
fun AveragesScreen(savedScores: List<Score>) {
    val averageTotal = savedScores.takeIf { it.isNotEmpty() }?.let { scores ->
        scores.map { it.total }.average().toInt()
    } ?: 0

    val averageStarters = savedScores.takeIf { it.isNotEmpty() }?.let { scores ->
        scores.map { it.starterCount }.average().toInt()
    } ?: 0

    val averageBonus = savedScores.takeIf { it.isNotEmpty() }?.let { scores ->
        scores.map { it.bonusCount }.average().toInt()
    } ?: 0

    val highestScore = savedScores.maxOfOrNull { it.total } ?: 0
    val lowestScore = savedScores.minOfOrNull { it.total } ?: 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            title = "Average Total",
            value = "$averageTotal",
            description = "Average of all scores"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Avg Starters",
                value = "$averageStarters",
                description = "Per game"
            )
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Avg Bonus",
                value = "$averageBonus",
                description = "Per game"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Highest",
                value = "$highestScore",
                description = "Best score"
            )
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Lowest",
                value = "$lowestScore",
                description = "Lowest score"
            )
        }

        // Games played card
        StatCard(
            title = "Games Played",
            value = "${savedScores.size}",
            description = "Total games recorded"
        )
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    description: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}