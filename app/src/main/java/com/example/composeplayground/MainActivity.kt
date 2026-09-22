package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeplayground.ui.theme.AccentOrange
import com.example.composeplayground.ui.theme.BgCream
import com.example.composeplayground.ui.theme.BottomNavBg
import com.example.composeplayground.ui.theme.ButtonPillBg
import com.example.composeplayground.ui.theme.CardWhite
import com.example.composeplayground.ui.theme.ComposeTestTheme
import com.example.composeplayground.ui.theme.TextDark
import com.example.composeplayground.ui.theme.TextSecondary
import com.example.composeplayground.ui.theme.UnfilledSegmentColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           NutritionDashboardScreen()
        }
    }
}

data class MealItem(
    val title: String,
    val time: String,
    val calories: Int,
    val goalPercent: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val emoji: String
)


@Composable
fun NutritionDashboardScreen() {
    val meals = listOf(
        MealItem("Lunch", "02:30 PM", 693, 35, 48, 83, 25, "🥗"),
        MealItem("BreakFast", "11:30 AM", 500, 25, 36, 57, 14, "🍳")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            DashboardTopBar()

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                item {
                    CalorieGaugeSection(
                        currentCalories = 1250,
                        goalCalories = 2000,
                        date = "20 Aug",
                        totalSegments = 8,
                        filledSegments = 5
                    )
                }

                item {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonPillBg,
                            contentColor = TextDark
                        ),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Meal",
                            tint = TextDark,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                items(meals) { meal ->
                    MealLogCard(meal = meal)
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        )
    }
}


@Composable
fun DashboardTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CardWhite),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "Calender",
                tint = TextDark,
                modifier = Modifier.size(22.dp)
            )
        }

        Text(
            text = "Dashboard",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CardWhite),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = TextDark,
                modifier = Modifier.size(22.dp)
            )

            Box(
                modifier = Modifier
                    .size(7.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-13).dp, y = 14.dp)
                    .clip(CircleShape)
                    .background(AccentOrange)
            )
        }
    }
}


@Composable
fun CalorieGaugeSection(
    currentCalories: Int,
    goalCalories: Int,
    date: String,
    totalSegments: Int = 8,
    filledSegments: Int = 5
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize())  {
            val strokeWidth = 46.dp.toPx()
            val diameter = size.width - strokeWidth - 40.dp.toPx()
            val arcSize = Size(diameter, diameter)
            val topLeft = Offset((size.width - diameter) / 2, 30.dp.toPx())

            val totalAngle = 180f
            
            // StrokeCap.Round adds length to the ends of the arc. 
            // We must calculate that added angle to ensure our gap is visible.
            val capAngle = (strokeWidth / diameter) * (180f / Math.PI.toFloat())
            val visualGapAngle = 2.5f 
            val spacingAngle = (capAngle * 2) + visualGapAngle

            val totalSpacing = spacingAngle * (totalSegments - 1)
            val sweepAnglePerSegment = ((totalAngle - totalSpacing) / totalSegments).coerceAtLeast(0.5f)

            for( i in 0 until totalSegments) {
                val startAngle = 180f + i * (sweepAnglePerSegment + spacingAngle)
                val color = if (i < filledSegments) AccentOrange else UnfilledSegmentColor

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAnglePerSegment,
                    useCenter =  false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = "Energy",
                tint = AccentOrange,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = date,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$currentCalories kcal",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Goal $goalCalories kcal",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = AccentOrange
            )
        }
    }
}

@Composable
fun MealLogCard(meal: MealItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3F1EC)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = meal.emoji,
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = meal.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = meal.time,
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${meal.calories} kcal",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "${meal.goalPercent}% of goal",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AccentOrange
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    MarcoStatItem(label = "Protein", value  = "${meal.protein}g")
                    MarcoStatItem(label = "Carbs", value = "${meal.carbs}g")
                    MarcoStatItem(label = "Fat", value = "${meal.fat}g")
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Meal",
                        tint = TextDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun MarcoStatItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(86.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(BottomNavBg),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = AccentOrange,
                    modifier = Modifier.size(26.dp)
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Log",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(AccentOrange),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "Scan Meal",
                    tint = BottomNavBg,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorites",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}









@Preview(showBackground = true, device = "id:pixel_7_pro")
@Composable
fun NutritionDashboardPreview() {
    NutritionDashboardScreen()
}
