package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.composeplayground.ui.theme.ComposeTestTheme
import com.example.ui.theme.PinkAccent
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                IceCreamShopScreen()
            }
        }
    }
}

data class CategoryItem(val id: Int, val name: String, val iconRes: Int)

data class DessertProduct(
    val id: Int,
    val name: String,
    val price: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)



@Composable
fun IceCreamShopScreen() {
    var selectedNavTab by remember { mutableIntStateOf(0) }

    val productList = remember {
        listOf(
            DessertProduct(
                id = 1,
                name = "Strawberry Delight",
                price = "$120.00",
                imageUrl = "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=600&q=80",
                isFavorite = true
            ),
            DessertProduct(
                id = 2,
                name = "Berry Sundae Swirl",
                price = "$95.00",
                imageUrl = "https://images.unsplash.com/photo-1501443762994-82bd5dace89a?auto=format&fit=crop&w=600&q=80",
                isFavorite = false
            ),
            DessertProduct(
                id = 3,
                name = "Chocolate Fudge Brownie",
                price = "$110.00",
                imageUrl = "https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=600&q=80",
                isFavorite = true
            ),
            DessertProduct(
                id = 6,
                name = "Mango Sorbet Splash",
                price = "$90.00",
                imageUrl = "https://images.unsplash.com/photo-1553177595-4de2bb0842b9?auto=format&fit=crop&w=600&q=80",
                isFavorite = true
            ),
            DessertProduct(
                id = 7,
                name = "Pistachio Gelato",
                price = "$105.00",
                imageUrl = "https://images.unsplash.com/photo-1497034825429-c343d7c6a68f?auto=format&fit=crop&w=600&q=80",
                isFavorite = false
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBE4E7))
    ) { 
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                HeaderSection(name = "Alex") }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                SearchBarSection(placeHolder = "Search")
            } }

            item {
                SectionHeader(title = "Categories", onSeeAll = {})
            }

            item {
                CategoriesRow(onCategoryClick = {})
            }

            items(productList) {product ->
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                    ProductCard(product = product)
                }
            }
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            FloatingBottomNavigation(
                selectedTab = selectedNavTab,
                onTabSelected = { selectedNavTab = it}
            )
        }

    }

}


@Composable
fun HeaderSection(name: String = "Alex") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
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
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Text(
                    text = "hey $name 👋",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Good Morning",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "cart Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(28.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .clickable{},
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "cart Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(28.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .offset(x = 6.dp, y  = (-6).dp)
                        .clip(CircleShape)
                        .background(Color.Red),
                )
            }
        }
    }
}

@Composable
fun SearchBarSection(
    modifier:Modifier = Modifier,
    initialQuery: String = "",
    placeHolder: String = "",
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
                tint = Color.Black.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                value = query,
                onValueChange = {query = it},
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                cursorBrush = SolidColor(PinkAccent),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onSearchSubmit(query)
                    }
                ),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment =  Alignment.CenterStart,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if(query.isEmpty()) {
                            Text(
                                text = placeHolder,
                                fontSize = 13.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal
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
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            query = ""
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
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
            color = TextPrimary
        )
        TextButton(onClick = onSeeAll) {
            Text(text = "See All", fontSize = 12.sp, color = TextSecondary)
        }
    }
}


@Composable
fun CategoriesRow(onCategoryClick: (CategoryItem) -> Unit) {
    val categories = listOf(
        "All" to "🍨",
        "Cones" to "🍦",
        "Scoops" to "🍧",
        "Cakes" to "🍰",
        "Gelato" to "🍨",
        "Sundae" to "🍧"
    )

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
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

    }
}


@Composable
fun ProductCard(
    product: DessertProduct,
    modifier:Modifier = Modifier,
    onAddToCart: () -> Unit = {},
    onToggleFavorite: () -> Unit = {},
    onDetailClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(290.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFFDD4DC))
            .clickable{}
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable{onAddToCart()},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "Add to Bag",
                    tint = Color(0xFF1F1F1F),
                    modifier = Modifier.size(20.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable{ onToggleFavorite()},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (product.isFavorite) Color(0xFFFF5271) else Color(0xFF1F1F1F),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(12.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(Color.White.copy(alpha = 0.90f))
                .padding(horizontal = 18.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        text = product.price,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }

                Box(
                    modifier  = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFFF8FA2), Color(0xFFFA5E7C))
                            )
                        )
                        .clickable{onDetailClick()},
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = "View Details",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(-45f)
                    )
                }
            }
        }
    }

}


@Composable
fun FloatingBottomNavigation(
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    val navItems = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.ShoppingBag,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.Person
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .height(64.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = Color(0x33FA5E7C)
                ),
            shape = RoundedCornerShape(32.dp),
            color = Color.White.copy(alpha = 0.85f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, icon ->
                    val isSelected = selectedTab == index

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if(isSelected) {
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFFF8FA2).copy(alpha = 0.6f),
                                            Color(0xFFFA5E7C).copy(alpha = 0.2f)
                                        )
                                    )
                                } else SolidColor(Color.Transparent)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember {  MutableInteractionSource()}
                            ) { onTabSelected(index)},
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) Color(0xFFFA5E7C) else Color(0xFF333333),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}