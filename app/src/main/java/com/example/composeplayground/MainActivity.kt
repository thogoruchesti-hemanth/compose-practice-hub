package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MiscellaneousServices
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.composeplayground.ui.theme.BrandBlueEnd
import com.example.composeplayground.ui.theme.BrandBlueStart
import com.example.composeplayground.ui.theme.ComposeTestTheme
import com.example.composeplayground.ui.theme.GradientSkyBottom
import com.example.composeplayground.ui.theme.GradientSkyMid
import com.example.composeplayground.ui.theme.GradientSkyTop
import com.example.composeplayground.ui.theme.PillBorder
import com.example.composeplayground.ui.theme.PrimaryBlue
import com.example.composeplayground.ui.theme.StarGold
import com.example.composeplayground.ui.theme.TextBlack
import com.example.composeplayground.ui.theme.TextMuted
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                ServiceHomeScreen()
            }
        }
    }
}

data class CategoryTabItem(
    val id: String,
    val title: String
)


@Composable
fun ServiceHomeScreen(modifier: Modifier = Modifier) {

    val categories = remember {
        listOf(
            CategoryTabItem("all", "All"),
            CategoryTabItem("electrician", "Electrician"),
            CategoryTabItem("plumber", "Plumber"),
            CategoryTabItem("ac_cooling", "Ac & Cooling")
        )
    }

// Hoisted state from ViewModel
    var selectedCategoryId by remember { mutableStateOf("all") }
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GradientSkyTop,
                        GradientSkyMid,
                        GradientSkyBottom
                    ),
                    endY = 1200f
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 90.dp) // Room for floating navbar
        ) {
            HeaderSection(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))

            Spacer(modifier = Modifier.height(8.dp))

            SearchBarSection(modifier = Modifier.padding(horizontal = 20.dp))

            Spacer(modifier = Modifier.height(16.dp))

            CategoryPillTabBar(
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                onCategorySelected = { category ->
                    selectedCategoryId = category.id
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            HeroBanner(modifier = Modifier.padding(horizontal = 20.dp))

            Spacer(modifier = Modifier.height(24.dp))

            OurServicesSection()
        }

        // Floating Bottom Navigation Bar
        FloatingBottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}


@Composable
fun CategoryPillTabBar(
    categories: List<CategoryTabItem>,
    selectedCategoryId: String,
    onCategorySelected: (CategoryTabItem) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp)
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(
            items = categories,
            key = { _, item -> item.id }
        ) { index, category ->
            val isSelected = category.id == selectedCategoryId

            CategoryPillTab(
                title = category.title,
                isSelected = isSelected,
                onClick = {
                    onCategorySelected(category)
                    // Auto-scrolls the tapped pill into full visibility
                    coroutineScope.launch {
                        listState.animateScrollToItem(index)
                    }
                }
            )
        }
    }
}

@Composable
private fun CategoryPillTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedTextColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else TextMuted,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
        label = "TabTextColor"
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Transparent else PillBorder,
        animationSpec = tween(durationMillis = 250),
        label = "TabBorderColor"
    )

    Box(
        modifier = Modifier
            .semantics {
                role = Role.Tab
                selected = isSelected
            }
            .clip(RoundedCornerShape(22.dp))
            .then(
                if (isSelected) {
                    Modifier
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(22.dp), spotColor = PrimaryBlue.copy(alpha = 0.4f))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(BrandBlueStart, BrandBlueEnd)
                            )
                        )
                } else {
                    Modifier
                        .background(Color.White)
                        .border(1.dp, animatedBorderColor, RoundedCornerShape(22.dp))
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Custom clean tap without raw ripple breaking the pill boundary
                onClick = onClick
            )
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = animatedTextColor
        )
    }

}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=200&q=80",
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy((-2).dp) 
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Hey, Hemanth",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextBlack,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "👋",
                        fontSize = 14.sp,
                    )
                }
//                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Good Morning",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted
                )
            }
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .clickable{},
            contentAlignment = Alignment.Center
        ) {
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
                    .background(PrimaryBlue)
            )
        }

    }
}

@Composable
fun SearchBarSection(
    modifier: Modifier = Modifier,
    initialQuery: String = "",
    placeHolder: String = "What Service Do You Need?",
    onSearchSubmit: (String) -> Unit = {}
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
                tint = TextBlack.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                value = query,
                onValueChange = { query = it},
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextBlack
                ),
                cursorBrush = SolidColor(PrimaryBlue),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onSearchSubmit(query)
                    }
                ),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = placeHolder,
                                fontSize = 13.sp,
                                color = TextMuted,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if (query.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            query = ""
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

        }
    }

}

@Composable
fun HeroBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF00C9FF),
                        Color(0xFF009EE0),
                        Color(0xFF0073E6)
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.hero_banner_image),
            contentDescription = "Service Man",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxHeight()
                .width(185.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.58f)
                .padding(start = 22.dp, top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Book a Service Man",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Fast, reliable repair & Care for your home and office",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.95f),
                    lineHeight = 16.sp
                )
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(22.dp),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "Book Service",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF009EE0)
                )
            }
        }
    }
}

@Composable
fun OurServicesSection() {

    val categories = remember {
        listOf(
            CategoryTabItem("repairing", "Repairing"),
            CategoryTabItem("installation", "Installation"),
            CategoryTabItem("rewiring", "Rewiring"),
            CategoryTabItem("service", "Service")
        )
    }

    var selectedCategoryId by remember { mutableStateOf("all") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Our Services",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Text(
                text = "View all",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextMuted,
                modifier = Modifier.clickable { }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        CategoryPillTabBar(
            categories = categories,
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = { category ->
                selectedCategoryId = category.id
                // Trigger viewModel.filterServices(category.id)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ServiceCard(
                imageUrl = "https://images.unsplash.com/photo-1621905252507-b35492cc74b4?auto=format&fit=crop&w=400&q=80",
                title = "Electrical Repair",
                price = "$15/hr",
                experience = "8 Years Experience",
                rating = "4.9"
            )

            ServiceCard(
                imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?auto=format&fit=crop&w=400&q=80",
                title = "Ac Repair",
                price = "$20/hr",
                experience = "5 Years Experience",
                rating = "4.8"
            )
        }


    }
}

@Composable
fun ServiceCard(
    imageUrl: String,
    title: String,
    price: String,
    experience: String,
    rating: String
) {

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(230.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )

                Text(
                    text = price,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = experience,
                    fontSize = 10.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = StarGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = rating,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextBlack
                    )
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
    CALENDAR("Calendar", Icons.Outlined.CalendarMonth),
    MESSAGES("Messages", Icons.Outlined.ChatBubbleOutline),
    PROFILE("Profile", Icons.Outlined.PersonOutline),
    SERVICES("Services", Icons.Outlined.MiscellaneousServices)
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

                if (isSelected) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(BrandBlueStart, BrandBlueEnd)
                                )
                            )
                            .clickable {
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
    onClick: () -> Unit = {}
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
            tint = TextBlack.copy(alpha = 0.8f),
            modifier = Modifier.size(20.dp)
        )
    }
}