package uk.co.weissmandl.uccounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import uk.co.weissmandl.uccounter.ui.theme.UCCounterTheme

class MainActivity : ComponentActivity() {
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

                total = starterCount + bonusCount


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
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
                            color = Color(0xFFbcfafc),
                            fontFamily = helveticaneue,
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
                            fontFamily = helveticaneue,
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
                            color = Color(0xFFbcfafc),
                            fontFamily = helveticaneue,
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Button(onClick = {
                            starterCount -= 10
                        }) {
                            Text("-10")
                        }
                        Button(onClick = {
                            bonusCount -= 5
                        }) {
                            Text("-5")
                        }
                        Button(onClick = {
                            starterCount = 0
                            bonusCount = 0
                            total = 0
                        }) {
                            Text("Reset")
                        }
                    }
                }
            }
        }
    }

@Composable
fun Question(title: String, questionType: Int, count: Int, onCountChange: (Int) -> Unit) {
    Button(onClick = {
        val newCount = count + questionType
        onCountChange(newCount)
    }) {
        Text(
            "$title   +$questionType",
        )
    }
    Text(
        text = "$count",
        fontSize = 30.sp,
    )
}}