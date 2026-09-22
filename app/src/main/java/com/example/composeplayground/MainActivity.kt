package com.example.composeplayground

import android.R.attr.tint
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeplayground.ui.theme.BgLightGray
import com.example.composeplayground.ui.theme.CardBlack
import com.example.composeplayground.ui.theme.CardGreen
import com.example.composeplayground.ui.theme.CashbackGreen
import com.example.composeplayground.ui.theme.ComposeTestTheme
import com.example.composeplayground.ui.theme.TextPrimary
import com.example.composeplayground.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                NeoBankHomeScreen()
            }
        }
    }
}

data class Transaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: String,
    val isExpense: Boolean = true,
    val iconEmoji: String,
    val cashback: String? = null
)

val sampleTransactions = listOf(
    Transaction(
        id = "1",
        title = "Starbucks Coffee",
        date = "October 17, 09:00 PM",
        amount = "-$44.80",
        isExpense = true,
        iconEmoji = "☕",
        cashback = "+$1.65"
    ),
    Transaction(
        id = "2",
        title = "Apple Store",
        date = "October 16, 02:15 PM",
        amount = "-$129.00",
        isExpense = true,
        iconEmoji = "💻",
        cashback = "+$3.80"
    ),
    Transaction(
        id = "3",
        title = "Salary Deposit",
        date = "October 15, 08:30 AM",
        amount = "+$2,850.00",
        isExpense = false,
        iconEmoji = "💰",
        cashback = null
    ),
    Transaction(
        id = "4",
        title = "Uber Rides",
        date = "October 14, 11:45 PM",
        amount = "-$18.40",
        isExpense = true,
        iconEmoji = "🚗",
        cashback = "+$0.55"
    ),
    Transaction(
        id = "5",
        title = "Netflix Subscription",
        date = "October 12, 06:10 PM",
        amount = "-$15.99",
        isExpense = true,
        iconEmoji = "🎬",
        cashback = null
    )
)

@Composable
fun NeoBankHomeScreen() {
    Scaffold(
        bottomBar = {NeobankBottomBar()},
        containerColor = BgLightGray
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            HeaderSection(name = "Hemanth")

            BalanceCard(balance = "$3,200.00")

            CardsHeaderSection()

            CardsListSection()

            TransactionSection()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}




@Composable
private fun HeaderSection(name: String) {

    Row(
       modifier = Modifier
           .fillMaxWidth()
           .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column{
            Text(
                text = "Good Morning,$name",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
                )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Welcome to Neobank",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(16.dp)
                    )
                .clickable{},
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notification",
                    tint = Color(0xFF1F2937),
                    modifier = Modifier.size(24.dp)
                )
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-1).dp, y = (1).dp)
                            .background(
                                color = Color(0xFF84CC16), // Lime green dot
                                shape = CircleShape
                            )
                    )
            }
        }
    }

}


@Composable
private fun BalanceCard(balance: String) {
    var isBalanceVisible by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {

            Text(
                text = "Your Balance",
                fontSize = 14.sp,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier=Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if(isBalanceVisible) balance else "*********",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary

                )
                IconButton(
                    onClick = {
                        isBalanceVisible = !isBalanceVisible
                    }
                ) {
                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = "Toggle Balance",
                        tint = TextPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {

                },
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Add money",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
private fun CardsHeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Your cards",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Row(
            modifier = Modifier.clickable{},
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "New Card",
                tint = TextPrimary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "New Card",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

        }
    }
}

@Composable
fun CardsListSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        BankCardItem(
            cardHolderType = "Debit Card",
            lastFourDigits = "4568",
            bgColor = CardGreen,
            textColor = Color(0xFF182905),
            watermarkColor = Color(0xFFB1DC32)
        )
        BankCardItem(
            cardHolderType = "Credit Card",
            lastFourDigits = "8921",
            bgColor = CardBlack,
            textColor = CardGreen,
            watermarkColor = Color(0xFF26282B)
        )
    }
}


@Composable
fun BankCardItem(
    cardHolderType: String,
    lastFourDigits: String,
    bgColor: Color,
    textColor: Color,
    watermarkColor: Color,
) {

    Card(
        modifier = Modifier
            .width(260.dp)
            .height(160.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        )
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {

            // Subtle "NEO" watermark background pattern text
            Text(
                text = "N. NEO. NEO.\nNEO. NEO. NEO.\nNEO. NEO. NEO.\nNEO. NEO. NEO.",
                fontSize = 20.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Black,
                color = watermarkColor.copy(alpha = 0.5f),
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "N.",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = textColor
                )

                Row {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color(0xFFEB001B), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .offset(x = (-8).dp)
                            .size(20.dp)
                            .background(Color(0xFFF79E1B).copy(alpha = 0.85f), CircleShape)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = cardHolderType,
                        fontSize = 11.sp,
                        color = textColor.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "•••• $lastFourDigits",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .clickable { }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Visibility,
                        contentDescription = "Details",
                        tint = Color(0xFF1E2124),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Details",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E2124)
                    )
                }

            }
        }
    }
}


@Composable
fun TransactionSection(
    transactions: List<Transaction> = sampleTransactions,
    onSeeAllClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Transactions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "See all",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    modifier = Modifier.clickable{}
                )
            }
            
            transactions.forEachIndexed { index, item ->
                TransactionRowItem(item = item)

                if (index < transactions.lastIndex) {
                    HorizontalDivider(
                        color = Color(0xFFF1F5F9),
                        thickness = 1.dp
                    )
                }
            }
         
        }
    }
}

@Composable
fun TransactionRowItem(item: Transaction) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp,Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                .background(Color(0xFFFAFAFA)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = item.iconEmoji,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.date,
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = item.amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            if(item.cashback != null) {
                Spacer(modifier = Modifier.height(3.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(CashbackGreen)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "+$1.65",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF264005)
                    )
                }
            }
        }
    }
}


@Composable
fun NeobankBottomBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        color = Color.White,
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(icon = Icons.Outlined.Home,label = "Home", isSelected = true)
            BottomNavItem(icon = Icons.Outlined.Map, label = "Map", isSelected = false)
            BottomNavItem(icon = Icons.Outlined.SwapHoriz, label = "Transfer", isSelected = false)
            BottomNavItem(icon = Icons.Outlined.Settings, label = "Settings", isSelected = false)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier =  Modifier.clickable{}

            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .border(1.5.dp,Color(0xFF8CC63F),CircleShape)
                        .padding(2.dp)
                        .background(Color(0xFFE5E7EB), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🙍", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Profile",
                    fontSize = 10.sp,
                    color = Color(0xFF8C95A3)
                )
            }
        }
    }
}


@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean
) {
    val tint = if(isSelected) Color(0xFF1E2124) else Color(0xFF94A3B8)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable{}
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = tint
        )
    }
}
