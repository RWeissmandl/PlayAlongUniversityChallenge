package uk.co.weissmandl.uccounter
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun HomeScreen(total: Int, starterCount: Int, bonusCount: Int, savedScores: List<MainActivity.Score>) {
//    Text(
//        text = "$total",
//        fontSize = 70.sp,
//        modifier = Modifier.fillMaxWidth(),
//        textAlign = TextAlign.Center
//    )
//    Spacer(modifier = Modifier.height(50.dp))
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Question("Starter Question", 10, starterCount, onCountChange = { newCount ->
//            starterCount = newCount
//        })
//    }
//    Spacer(modifier = Modifier.height(30.dp))
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Question("Bonus Question", 5, bonusCount, onCountChange = {newCount ->
//            bonusCount = newCount
//        })
//    }
//    Spacer(modifier = Modifier.weight(1f))
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        homePageButton(
//            onClick = { val newScore = MainActivity.Score(total, starterCount, bonusCount)
//                savedScores = savedScores + newScore
//                starterCount = 0
//                bonusCount = 0
//                total = 0
//            },
//            text = "Save"
//        )}
//    Spacer(modifier = Modifier.weight(1f))
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(15.dp)
//    ) {
//        homePageButton(
//            onClick = { bonusCount -= 10 },
//            text = "-10"
//        )
//        homePageButton(
//            onClick = { bonusCount -= 5 },
//            text = "-5"
//        )
//        homePageButton(
//            onClick = {
//                starterCount = 0
//                bonusCount = 0
//                total = 0 },
//            text = "Reset"
//        )
//    }
//}
