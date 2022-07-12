package com.redbadger.badgerme_jetpack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.ui.theme.UiDarker

@Composable
fun Title() {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Box () {
            Column (modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.orange_box),
                    contentDescription = "Title flair",
                    modifier = Modifier
                        .padding(start = 80.dp)
                        .size(40.dp)
                )
            }
            Column (modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Badger Me",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun BottomBar(activeTab: Int) {
    val tabs = listOf("Home", "Badgers", "Profile", "Activities")
    val tabHeight = 56
    Box(Modifier.height(tabHeight.dp)) {
        Column {
            Row(Modifier.fillMaxWidth()) {
                TabRow(
                    selectedTabIndex = activeTab,
                    backgroundColor = Color.Black,
                    contentColor = MaterialTheme.colors.primary,
                    indicator = { tabPositions: List<TabPosition> ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[activeTab])
                                .offset(y = (-(tabHeight-2)).dp)
                                .height(2.dp)
                                .background(color = MaterialTheme.colors.primary)
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, it ->
                        Tab(
                            selected = true,
                            onClick = { /*TODO*/ },
                            text = {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.caption,
                                    color = if (index == activeTab) Color.White
                                            else UiDarker
                                )
                            },
                            icon = {
                                Image(
                                    painter = painterResource(
                                        id = when(it) {
                                            "Home" -> R.drawable.bottom_bar_home
                                            "Badgers" -> R.drawable.bottom_bar_badgers
                                            "Profile" -> R.drawable.bottom_bar_profile
                                            "Activities" -> R.drawable.bottom_bar_activity
                                            else -> R.drawable.bottom_bar_home
                                        }
                                    ),
                                    contentDescription = "Home icon",
                                    colorFilter = ColorFilter.tint(
                                        if (index == activeTab) Color.White
                                        else UiDarker
                                    )
                                )
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BadgerMe_JetpackTheme {
        BottomBar(0)
    }
}

@Preview(showBackground = true)
@Composable
fun TitlePreview() {
    BadgerMe_JetpackTheme {
        Title()
    }
}