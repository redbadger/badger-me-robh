package com.redbadger.badgerme_jetpack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
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
    Box(Modifier.height(56.dp)) {
        Column {
            Row(Modifier.fillMaxWidth()) {
                TabRow(
                    selectedTabIndex = activeTab,
                    backgroundColor = Color.Black,
                    contentColor = MaterialTheme.colors.primary,
//                    modifier = Modifier.width(210.dp)
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
                            }
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