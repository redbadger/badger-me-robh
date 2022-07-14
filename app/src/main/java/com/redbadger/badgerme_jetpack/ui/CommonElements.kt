package com.redbadger.badgerme_jetpack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.navigation.Screen
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
fun BottomBar(
    activeTab: MutableState<Int>,
    navHostController: NavHostController?,
    userId: String?,
    authToken: String
) {
    val tabs = listOf("home", "badgers", "profile", "activities")
    val tabHeight = 56
    Box(Modifier.height(tabHeight.dp)) {
        Column {
            Row(Modifier.fillMaxWidth()) {
                TabRow(
                    selectedTabIndex = activeTab.value,
                    backgroundColor = Color.Black,
                    contentColor = MaterialTheme.colors.primary,
                    indicator = { tabPositions: List<TabPosition> ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[activeTab.value])
                                .offset(y = (-(tabHeight - 2)).dp)
                                .height(2.dp)
                                .background(color = MaterialTheme.colors.primary)
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, it ->
                        Tab(
                            selected = true,
                            onClick = {
//                                navHostController!!.navigate("${it}/${userId}/${authToken}")
                                  activeTab.value = index
                            },
                            text = {
                                Text(
                                    text = it.replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.caption,
                                    color = if (index == activeTab.value) Color.White
                                            else UiDarker
                                )
                            },
                            icon = {
                                Image(
                                    painter = painterResource(
                                        id = when(it) {
                                            "home" -> R.drawable.bottom_bar_home
                                            "badgers" -> R.drawable.bottom_bar_badgers
                                            "profile" -> R.drawable.bottom_bar_profile
                                            "activities" -> R.drawable.bottom_bar_activity
                                            else -> R.drawable.bottom_bar_home
                                        }
                                    ),
                                    contentDescription = "Home icon",
                                    colorFilter = ColorFilter.tint(
                                        if (index == activeTab.value) Color.White
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
        BottomBar(remember { mutableStateOf(0) }, null, "", "")
    }
}

@Preview(showBackground = true)
@Composable
fun TitlePreview() {
    BadgerMe_JetpackTheme {
        Title()
    }
}