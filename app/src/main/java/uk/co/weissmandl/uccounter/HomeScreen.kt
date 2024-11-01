package uk.co.weissmandl.uccounter

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "$total",
            fontSize = 70.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))

        // Starter Question
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Question("Starter Question", 10, starterCount, onCountChange = { newCount ->
                viewModel.updateStarterCount(newCount)
            })
        }
        Spacer(modifier = Modifier.height(30.dp))

        // Bonus Question
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Question("Bonus Question", 5, bonusCount, onCountChange = { newCount ->
                viewModel.updateBonusCount(newCount)
            })
        }
        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            homePageButton(onClick = { saveConfirmation.value = true }, text = "Save")
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            homePageButton(onClick = { viewModel.updateBonusCount(bonusCount - 10) }, text = "-10")
            homePageButton(onClick = { viewModel.updateBonusCount(bonusCount - 5) }, text = "-5")
            homePageButton(onClick = {
                viewModel.updateStarterCount(0)
                viewModel.updateBonusCount(0)
            }, text = "Reset")
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
