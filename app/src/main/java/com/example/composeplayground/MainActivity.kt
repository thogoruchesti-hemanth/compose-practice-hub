package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.composeplayground.ui.theme.ComposeTestTheme
import com.example.composeplayground.ui.theme.DarkText
import com.example.composeplayground.ui.theme.ExpenseRed
import com.example.composeplayground.ui.theme.MintBackgroundDark
import com.example.composeplayground.ui.theme.MintBackgroundLight
import com.example.composeplayground.ui.theme.MutedText
import com.example.composeplayground.ui.theme.NeonYellowCard
import com.example.composeplayground.ui.theme.PositiveGreen
import com.example.composeplayground.ui.theme.md_theme_light_error
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                WalletScreen()
            }
        }
    }
}

data class Transaction(
    val id: Int,
    val title: String,
    val time: String,
    val amount: String,
    val logoText: String,
    val logoBgColor: Color = Color(0xFF14171A),
    val logoTextColor: Color = Color(0xFF4EE1A0)
)


@Composable
fun WalletScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MintBackgroundLight, MintBackgroundDark,MintBackgroundLight)
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            HeaderSection("Hemanth")
            Spacer(Modifier.height(20.dp))
            MainBalanceCard()
            Spacer(Modifier.height(28.dp))
            LatestTransactions()
            Spacer(Modifier.height(28.dp))
            CurrencySection()
        }

        FloatingBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
        )
    }
}


@Composable
fun HeaderSection(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2D8876)),
                contentAlignment = Alignment.Center
            ) {
                Text("L", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Text(
                "Hi, $name!",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = DarkText,
                    modifier = Modifier.size(24.dp)
                )
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .offset(x = (-2).dp, y = 1.dp)
                        .clip(CircleShape)
                        .background(md_theme_light_error)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ShowChart,
                contentDescription = null,
                tint = DarkText,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun BalanceCard(
    balance: String
) {
    var isBalanceVisible by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp)),
        colors = CardDefaults.cardColors(
            containerColor = NeonYellowCard,
            contentColor = DarkText
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "USD",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    imageVector = if (isBalanceVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = "Toggle Balance",
                    tint = DarkText,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isBalanceVisible = !isBalanceVisible }
                )
            }
            Text(
                text = "1USD = EUR 0.95 = GBR 0.79",
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Text(
                text = if (isBalanceVisible) balance else "*****",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Text(
                "+$421.03",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = DarkText
            )
        }
    }
}

@Composable
fun CardActionButton(
    icon: ImageVector,
    text: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$text Button",
            tint = Color.Gray,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
    }

}

@Composable
fun MainBalanceCard() {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(modifier = Modifier.padding(10.dp)) {

            BalanceCard(balance = "$26,887.09")
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CardActionButton(icon = Icons.Outlined.Paid,text = "Pay")
                CardActionDivider()
                CardActionButton(icon = Icons.Default.SwapHoriz,text = "Transfer")
                CardActionDivider()
                CardActionButton(icon = Icons.AutoMirrored.Filled.CallReceived,text = "Receive")

            }
        }
    }
}

@Composable
private fun CardActionDivider() {
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(2.dp)
            .background(Color(0xFFEEEEEE))
    )
}

@Composable
fun LatestTransactions() {
    val transactions = remember {
        listOf(
            Transaction(1, "Megogo", "1 min ago", "-$24.99", "M"),
            Transaction(2, "Spotify", "2 hours ago", "-$9.99", "S", Color(0xFF1DB954), Color.White),
            Transaction(3, "Apple Store", "Yesterday", "-$129.00", "A", Color(0xFF000000), Color.White),
            Transaction(4, "Freelance", "2 days ago", "+$850.00", "F", Color(0xFF27AE60), Color.White)
        )
    }

    var currentIndex by remember { mutableStateOf(0)}
    val offSetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Latest Transactions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Text(
                text = "See All",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MutedText
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(86.dp)
                .pointerInput(currentIndex) {
                    detectVerticalDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                val dragThreshold = 50f
                                if (offSetY.value < -dragThreshold && currentIndex < transactions.size - 1) {
                                    // Smooth snappy exit without bouncing in the air
                                    offSetY.animateTo(-250f, tween(150, easing = FastOutSlowInEasing))
                                    currentIndex++
                                    offSetY.snapTo(0f)
                                } else if (offSetY.value > dragThreshold && currentIndex > 0) {
                                    // Smooth snappy exit without bouncing in the air
                                    offSetY.animateTo(250f, tween(150, easing = FastOutSlowInEasing))
                                    currentIndex--
                                    offSetY.snapTo(0f)
                                } else {
                                    // Spring back to center smoothly if threshold wasn't reached
                                    offSetY.animateTo(
                                        0f,
                                        spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    )
                                }
                            }
                        },
                        onVerticalDrag = { _, dragAmount ->
                            coroutineScope.launch {
                                // Apply drag resistance / dampening so cards don't fly up and down too much
                                offSetY.snapTo(offSetY.value + (dragAmount * 0.45f))
                            }
                        }
                    )
                },
            contentAlignment = Alignment.TopCenter
        ) {
            val maxVisible = 3
            for(offset in (maxVisible -1 ) downTo 0) {
                val itemIndex = currentIndex + offset
                if(itemIndex < transactions.size) {
                    val item = transactions[itemIndex]
                    val isTopCard = offset == 0

                    val cardScale = 1f - (offset * 0.04f)
                    val cardVerticalOffset = (offset * 8).dp

                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isTopCard) Color.White else Color(0xFFF7FAF8)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isTopCard) 4.dp else (3 - offset).dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .zIndex((maxVisible - offset).toFloat())
                            .offset(y = cardVerticalOffset)
                            .scale(cardScale)
                            .graphicsLayer {
                                if (isTopCard) {
                                    translationY = offSetY.value
                                    // Smooth alpha fade on drag
                                    alpha = 1f - (kotlin.math.abs(offSetY.value) / 250f).coerceIn(
                                        0f,
                                        0.5f
                                    )
                                }
                            }
                    ) {
                        TransactionCardContent(item = item)
                    }
                }
            }
        }
    }
}


@Composable
fun TransactionCardContent(item: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(item.logoBgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.logoText,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = item.logoTextColor
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Text(
                text = item.time,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
        Text(
            text = item.amount,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = ExpenseRed
        )
    }

}

@Composable
fun CurrencySection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Currency",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
           CurrencyTile(
               modifier = Modifier.weight(1f),
               symbol = "€",
               symbolColor = PositiveGreen,
               currencyName = "Euro",
               rate = "0.97"
           )
            CurrencyTile(
               modifier = Modifier.weight(1f),
               symbol = "£",
               symbolColor = Color(0xFF6B8AFD),
               currencyName = "British Pound",
               rate = "0.82"
           )
            AddCurrencyTile(
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
fun CurrencyTile(
    modifier: Modifier = Modifier,
    symbol: String,
    symbolColor: Color,
    currencyName: String,
    rate: String
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = modifier.height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(symbolColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = symbol,
                    color = symbolColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(
                    text = currencyName,
                    fontSize = 12.sp,
                    color = MutedText,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = rate,
                    fontSize = 16.sp,
                    color = DarkText
                )
            }
        }
    }
}


@Composable
fun AddCurrencyTile(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF191C1F) ),
        modifier = modifier
            .height(140.dp)
            .clickable {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Currency",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Add\nCurrency",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
private fun FloatingBottomBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left small round button
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF6F8F7))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "↙",
                fontSize = 16.sp,
                color = DarkText
            )
        }

        // Center active button (Dark pill/circle)
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(DarkText)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "Home",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }

        // Right small round button
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF6F8F7))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "↗",
                fontSize = 16.sp,
                color = DarkText
            )
        }
    }
}


@Preview
@Composable
fun PreviewLayout() {
    FloatingBottomBar()
}