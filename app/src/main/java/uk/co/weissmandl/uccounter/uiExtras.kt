package uk.co.weissmandl.uccounter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import uk.co.weissmandl.uccounter.ui.theme.blueColour
import uk.co.weissmandl.uccounter.ui.theme.greyColour
import uk.co.weissmandl.uccounter.ui.theme.yellowColour

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Scores,
        BottomNavItem.Averages
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

enum class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    Home("home", Icons.Default.Home, "Play"),
    Scores("scores", Icons.Default.List, "Scores"),
    Averages("averages", Icons.Default.CheckCircle, "Averages")
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
                .width(IntrinsicSize.Max)
                .wrapContentHeight(),
            overflow = TextOverflow.Clip
        )
    }
}

@Composable
fun BannerRajan(fontFamily: FontFamily, total: String) {
    Box(
        modifier = Modifier
            .background(color = greyColour)
            .padding(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .height(60.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = blueColour, shape = RoundedCornerShape(12.dp))
                    .border(4.dp, color = greyColour, shape = RoundedCornerShape(12.dp))
                    .height(55.dp)
                    .width(80.dp)
            ) {
                Text(
                    text = "UC",
                    color = Color.Black,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .background(color = yellowColour, shape = RoundedCornerShape(12.dp))
                    .border(4.dp, color = greyColour, shape = RoundedCornerShape(12.dp))
                    .weight(1f)
                    .height(55.dp)
            ) {
                Text(
                    text = "University Challenge",
                    color = Color.Black,
                    fontFamily = fontFamily,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .background(color = blueColour, shape = RoundedCornerShape(12.dp))
                    .border(4.dp, color = greyColour, shape = RoundedCornerShape(12.dp))
                    .height(50.dp)
                    .width(50.dp)
            ) {
                Text(
                    text = total,
                    color = Color.Black,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}





