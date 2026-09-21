package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeplayground.ui.theme.BackgroundDark
import com.example.composeplayground.ui.theme.SubtleGray
import com.example.composeplayground.ui.theme.SurfaceCard
import com.example.composeplayground.ui.theme.TextMuted
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoDashboardScreen()
        }
    }
}

data class CryptoCardData(
    val name: String,
    val amount: String,
    val iconColor: Color
)

data class TransactionItemData(
    val name: String,
    val cryptoAmount: String,
    val fiatAmount: String,
    val changePercent: String,
    val iconColor: Color
)


@Composable
fun CryptoDashboardScreen() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                color = SurfaceCard
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 24.dp)
                ) {
                    TopProfileHeader()

                    Spacer(modifier = Modifier.height(20.dp))

                    BalanceSection(visible)

                    Spacer(modifier = Modifier.height(20.dp))

                    CryptoHorizontalCards()


                }
            }
            QuickActionBar()

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
                color = SurfaceCard
            ) {
                RecentActionsList()
            }
        }
    }

}


@Composable
private fun TopProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFE2E2E2)),
            contentAlignment = Alignment.Center
        ) {
            Text("🧑‍🦰", fontSize = 20.sp)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            HeaderIconButton(Icons.Outlined.Home)
            HeaderIconButton(Icons.Outlined.Notifications)
        }
    }
}


@Composable
private fun HeaderIconButton(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(SubtleGray)
            .clickable {

            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Black
        )
    }
}


@Composable
private fun BalanceSection(animate: Boolean) {
    val balance = remember { Animatable(0f) }

    LaunchedEffect(animate) {
        if (animate) {
            balance.animateTo(
                targetValue = 25431.20f,
                animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Available Balance",
            color = TextMuted,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "$",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp, end = 2.dp)
                )

                Text(
                    text = String.format(Locale.US, "%.2f", balance.value),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Surface(
                shape = RoundedCornerShape(50),
                color = SubtleGray
            ) {
                Text(
                    text = "+2.5%",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
private fun CryptoHorizontalCards() {
    val items = listOf(
        CryptoCardData("Cardano", "50.50", Color(0xFF0033AD)),
        CryptoCardData("Ethereum", "30.00", Color(0xFF454A75)),
        CryptoCardData("Tether", "30.00", Color(0xFF26A17B)),
        CryptoCardData("Cardano", "50.50", Color(0xFF0033AD)),
        CryptoCardData("Ethereum", "30.00", Color(0xFF454A75)),
        CryptoCardData("Tether", "30.00", Color(0xFF26A17B))
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(items) { _, crypto ->
            Card(
                shape = RoundedCornerShape(20),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .width(115.dp)
                    .height(130.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(crypto.iconColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("◆", color = Color.White, fontSize = 14.sp)
                    }

                    Column {
                        Text(
                            text = crypto.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = crypto.amount,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionPill(
            text = "Transfer",
            icon = Icons.AutoMirrored.Filled.CallMade,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {

                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
        ActionPill(
            text = "Received",
            icon = Icons.AutoMirrored.Filled.CallReceived,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionPill(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(52.dp)
            .clickable {},
        shape = RoundedCornerShape(26.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun RecentActionsList() {
    val transactions = listOf(
        TransactionItemData("Bitcoin", "0.0021BTC", "$217.00", "0.24%", Color(0xFFF7931A)),
        TransactionItemData("Ethereum", "1.59ETH", "$2500.00", "0.04%", Color(0xFF627EEA)),
        TransactionItemData("Tether", "1233.91USDT", "$1232.00", "1.24%", Color(0xFF26A17B)),
        TransactionItemData("XRP", "9708.65XRP", "$10230.00", "0.2%", Color(0xFF23292F)),
        TransactionItemData("SHIBA INU", "0.005418SHIB", "$1290.00", "0.50%", Color(0xFFFFA409)),
        TransactionItemData("Bitcoin", "0.0021BTC", "$217.00", "0.24%", Color(0xFFF7931A)),
        TransactionItemData("Ethereum", "1.59ETH", "$2500.00", "0.04%", Color(0xFF627EEA)),
        TransactionItemData("Tether", "1233.91USDT", "$1232.00", "1.24%", Color(0xFF26A17B)),
        TransactionItemData("XRP", "9708.65XRP", "$10230.00", "0.2%", Color(0xFF23292F)),
        TransactionItemData("SHIBA INU", "0.005418SHIB", "$1290.00", "0.50%", Color(0xFFFFA409)),
        TransactionItemData("Bitcoin", "0.0021BTC", "$217.00", "0.24%", Color(0xFFF7931A)),
        TransactionItemData("Ethereum", "1.59ETH", "$2500.00", "0.04%", Color(0xFF627EEA)),
        TransactionItemData("Tether", "1233.91USDT", "$1232.00", "1.24%", Color(0xFF26A17B)),
        TransactionItemData("XRP", "9708.65XRP", "$10230.00", "0.2%", Color(0xFF23292F)),
        TransactionItemData("SHIBA INU", "0.005418SHIB", "$1290.00", "0.50%", Color(0xFFFFA409))

    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Action",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = SubtleGray,
                    modifier = Modifier.clickable {}
                ) {
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)

                    )
                }
            }
        }

        items(transactions) { tx ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(tx.iconColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text("◆", color = Color.White, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tx.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = tx.cryptoAmount,
                        fontSize = 12.sp,
                        color = TextMuted

                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = tx.fiatAmount,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = tx.changePercent,
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
private fun previewFun() {
    CryptoDashboardScreen()
}