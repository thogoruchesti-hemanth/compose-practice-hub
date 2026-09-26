package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeplayground.ui.theme.BackgroundBottom
import com.example.composeplayground.ui.theme.BackgroundTop
import com.example.composeplayground.ui.theme.ComposeTestTheme
import com.example.composeplayground.ui.theme.DarkText
import com.example.composeplayground.ui.theme.LightText
import com.example.composeplayground.ui.theme.OrangeGradient
import com.example.composeplayground.ui.theme.OrangePrimary
import com.example.composeplayground.ui.theme.PromoCardBg

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                Scaffold { innerPadding ->
                    FoodDeliveryHomeScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


data class CategoryItem(val id: Int, val name: String, val iconRes: Int)


@Composable
fun FoodDeliveryHomeScreen(
    modifier: Modifier  = Modifier,
    onCategoryClick: (CategoryItem) -> Unit = {},
    onOrderClick: (PopularFood) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundTop, BackgroundBottom)
                )
            )
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item{
                Spacer(modifier = Modifier.statusBarsPadding())
                TopAppBarSection()
            }
            item {
                SearchBarSection()
            }
            item {
                PromoBannerSection()
            }
            item {
                SectionHeader(title = "Food Categories", onSeeAll = {})
                CategoriesRow(onCategoryClick = onCategoryClick)
            }
            item {
                SectionHeader(title = "Popular Food", onSeeAll = {})
                PopularFoodRow(onOrderClick = onOrderClick)
            }
        }

        FloatingBottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
                .navigationBarsPadding()
        )
    }
}

@Composable
fun TopAppBarSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = DarkText
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Current Location",
                    fontSize = 12.sp,
                    color = LightText
                )
                Text(
                    text = "New York, USA",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            HeaderIconButton(icon = Icons.Outlined.ShoppingCart)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .clickable{},
                contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .offset(x = 6.dp, y = (-6).dp)
                        .clip(CircleShape)
                        .background(Color.Red, CircleShape)
                )
            }
        }
    }
}

@Composable
fun HeaderIconButton(icon: ImageVector ) {
    Surface(
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.size(44.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(imageVector = icon, contentDescription = null, tint = DarkText)
        }
    }
}

@Composable
fun SearchBarSection(
    modifier: Modifier = Modifier,
    initialQuery: String = "",
    placeHolder: String = "Search Anything Here...",
    searchSubmit: (String) -> Unit = {}
) {

    var query by remember { mutableStateOf(initialQuery) }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(26.dp),
                spotColor = Color(0x1A000000)
            ),
        shape = RoundedCornerShape(26.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = DarkText.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                modifier = Modifier.weight(1f),
                value = query,
                onValueChange = {query = it},
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkText
                ),
                cursorBrush = SolidColor(OrangePrimary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        searchSubmit(query)
                    }
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = placeHolder,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = LightText
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if(query.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .clickable(
                            indication = null,
                            interactionSource =  remember { MutableInteractionSource() }
                        ) {
                            query = ""
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search",
                        tint = LightText,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun PromoBannerSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp,
                vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = PromoCardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hurry Up! Get 20% off",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkText
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Fresh Food everyday\nin our Foodzzy",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = DarkText),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp)
                ) {
                    Text( text = "Get Now", fontSize = 12.sp, color = Color.White)
                }
            }

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🛵", fontSize = 54.sp)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )
        TextButton(onClick = onSeeAll) {
            Text(text = "See All", fontSize = 12.sp, color = LightText)
        }
    }
}

@Composable
fun CategoriesRow(onCategoryClick: (CategoryItem) -> Unit) {
    val categories = listOf("See all" to "🍱", "Burger" to "🍔", "Pizza" to "🍕", "Sea Food" to "🍤", "Ice Cream" to "🍨")

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.size(60.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = item.second, fontSize = 28.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.first,
                    fontSize = 14.sp,
                    color = DarkText,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

data class PopularFood(
    val id: String,
    val title: String,
    val discount: String,
    val deliveryTime: String,
    val price: String,
    val imageRes: Int = R.drawable.ic_launcher_foreground
)

val ProPopularCardGradient = Brush.linearGradient(
    colorStops = arrayOf(
        0.0f to Color(0xFFFFFDFC), // Pure warm white highlight
        0.45f to Color(0xFFFFF1E6), // Gentle peach body
        1.0f to Color(0xFFFFDEC9)  // Rich, clean base tone (replaces muddy 0xFFFFD1B8)
    ),
    start = Offset.Zero,
    end = Offset.Infinite // Smooth 45-degree angle
)

@Composable
fun PopularFoodRow(onOrderClick: (PopularFood) -> Unit = {}) {
    val popularFoods = remember {
        listOf(
            PopularFood(
                id = "1",
                title = "Golden Melt\nSupreme Stack",
                discount = "30% Off",
                deliveryTime = "30",
                price = "$4.50",
                imageRes = R.drawable.burger
            ),
            PopularFood(
                id = "2",
                title = "Quick Grill\nBox",
                discount = "20% Off",
                deliveryTime = "20",
                price = "$2.50",
                imageRes = R.drawable.kebab
            ),
            PopularFood(
                id = "3",
                title = "Crispy Chicken\nZing Burger",
                discount = "15% Off",
                deliveryTime = "25",
                price = "$5.20",
                imageRes = R.drawable.chicken
            ),
            PopularFood(
                id = "4",
                title = "Cheesy Pepperoni\nPizza Slice",
                discount = "25% Off",
                deliveryTime = "15",
                price = "$3.80",
                imageRes = R.drawable.pizza
            ),
            PopularFood(
                id = "5",
                title = "Spicy Taco\nDeluxe Wrap",
                discount = "10% Off",
                deliveryTime = "18",
                price = "$3.20",
                imageRes = R.drawable.tacos
            )
        )
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = popularFoods,
            key = { it.id }
        ) { food ->
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .height(250.dp)
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(28.dp), ambientColor = Color(0xFFE07A5F).copy(alpha = 0.08f), spotColor = Color(0xFF3D2314).copy(alpha = 0.12f))
                    .clip(RoundedCornerShape(28.dp))
                    .background(ProPopularCardGradient)
                    .padding(18.dp)
            ) {
                // Rotated Discount Tag in Top Right
                Text(
                    text = food.discount,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-4).dp, y = 2.dp)
                        .rotate(28f)
                )

                // Food Image directly rendered without background container or clipping
                Image(
                    painter = painterResource(id = food.imageRes),
                    contentDescription = food.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(135.dp)
                        .offset(x = 10.dp, y = 10.dp)
                )

                // Left Column Info
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.60f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = food.title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText,
                        lineHeight = 22.sp
                    )

                    Column {
                        Text(
                            text = "Free Delivery",
                            fontSize = 11.sp,
                            color = DarkText,
                            fontWeight = FontWeight.Normal
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = food.deliveryTime,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "mins",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkText
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "Price",
                            fontSize = 11.sp,
                            color = DarkText,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = food.price,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DarkText
                        )
                    }

                    Button(
                        onClick = { onOrderClick(food) },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkText),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = "Order Now",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

enum class BottomNavItem(
    val title: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Outlined.Home),
    CALENDAR("Calendar", Icons.Outlined.ShoppingCart),
    MESSAGES("Messages", Icons.Outlined.MailOutline),
    PROFILE("Profile", Icons.Outlined.FavoriteBorder),
    SERVICES("Services", Icons.Outlined.Person)
}


@Composable
fun FloatingBottomNavigationBar(
    modifier: Modifier = Modifier,
    initialSelected: BottomNavItem = BottomNavItem.HOME,
    onItemSelected: (BottomNavItem) -> Unit = {}
) {
    var currentSelected by remember { mutableStateOf(initialSelected) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp),
        color = Color.White
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomNavItem.entries.forEach { item ->
                val isSelected = item == currentSelected

                if(isSelected) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(OrangeGradient)
                            .clickable{
                                currentSelected = item
                                onItemSelected(item)
                            }
                            .padding(horizontal = 16.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.title,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    NavIconCircle(
                        icon = item.icon,
                        desc = item.title,
                        onClick = {
                            currentSelected = item
                            onItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun NavIconCircle(
    icon: ImageVector,
    desc: String,
    onClick: () ->  Unit = {}
) {

    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = desc,
            tint = DarkText.copy(alpha = 0.8f),
            modifier = Modifier.size(20.dp)
        )
    }
}

