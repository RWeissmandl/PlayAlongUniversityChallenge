package uk.co.weissmandl.uccounter

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    var datePicker = remember { mutableStateOf(false) }
    var saveConfirmation = remember { mutableStateOf(false) }
    var selectedDate: LocalDate = LocalDate.now()
    val formattedDate = formatDate((selectedDate.toString()))
    val starterCount by viewModel.starterCount.observeAsState(0)
    val bonusCount by viewModel.bonusCount.observeAsState(0)
    val total by viewModel.total.observeAsState(0)
    val helveticaneue = FontFamily(Font(R.font.helveticaneue, FontWeight.Bold))

    LaunchedEffect(selectedDate) {
        viewModel.selectedDate = selectedDate
    }
    if (datePicker.value) {
        DatePickerDialog(
            onDismissRequest = { datePicker.value = false },
            onDateChange = { year, month, day ->
                selectedDate = LocalDate.of(year, month + 1, day)
                viewModel.selectedDate = selectedDate
                datePicker.value = false
            }
        )
    }

    if (saveConfirmation.value) {
        AlertDialog(
            onDismissRequest = { saveConfirmation.value = false },
            title = { Text(text = "Confirm Save") },
            text = {
                Column {
                    Text(text = "Do you want to save the score with today's date: $formattedDate?")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = { datePicker.value = true }
                    ) {
                        Text(text = "Change Date")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.saveScore()
                    saveConfirmation.value = false
                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { saveConfirmation.value = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    Box(modifier = Modifier
        .fillMaxSize(),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Total
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TOTAL SCORE",
                        style = MaterialTheme.typography.titleMedium,
                        //color = MaterialTheme.colorScheme.onPrimaryContainer
                        color = Color.DarkGray,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "$total",
                        fontSize = 68.sp,
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Starter & Bonus Questions
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ScoreCategory(
                        title = "Starter Questions",
                        score = starterCount,
                        points = 10,
                        onAdd = { viewModel.updateStarterCount(starterCount + 10) }
                    )
                    Divider()
                    ScoreCategory(
                        title = "Bonus Questions",
                        score = bonusCount,
                        points = 5,
                        onAdd = { viewModel.updateBonusCount(bonusCount + 5) }
                    )
                }
            }

            // Other buttons (-10, -5, reset)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuickActionButton(
                        text = "-10",
                        onClick = { viewModel.updateBonusCount(bonusCount - 10) }
                    )
                    QuickActionButton(
                        text = "-5",
                        onClick = { viewModel.updateBonusCount(bonusCount - 5) }
                    )
                    QuickActionButton(
                        text = "Reset",
                        onClick = {
                            viewModel.updateStarterCount(0)
                            viewModel.updateBonusCount(0)
                        }
                    )
                }
            }

            // Save Button
            Button(
                onClick = { saveConfirmation.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "SAVE SCORE",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun ScoreCategory(
    title: String,
    score: Int,
    points: Int,
    onAdd: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Score: $score",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Button(
            onClick = onAdd,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = "+$points")
        }
    }
}

@Composable
fun QuickActionButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text)
    }
}

@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateChange: (year: Int, month: Int, day: Int) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, day ->
            onDateChange(year, month, day)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        setOnDismissListener { onDismissRequest() }
        show()
    }
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
