package com.redbadger.badgerme_jetpack.ui.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme

@Composable
fun BadgerEventsView(navHostController: NavHostController?) {
    val tabs = listOf("Today", "Upcoming")
    Box {
        Column (Modifier.padding(start = 16.dp, top = 7.5.dp, end = 16.dp)) {
            Text(
                text = "Badger events",
                style = MaterialTheme.typography.h3
            )
            Row(modifier = Modifier.height(48.dp)) {
                TabRow(
                    selectedTabIndex = 0,
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary,
                    modifier = Modifier.width(210.dp)
                ) {
                    tabs.forEachIndexed { index, it ->
                        Tab(
                            selected = true,
                            onClick = { /*TODO*/ },
                            text = {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.button,
                                    color = Color.Black
                                )
                            }
                        )
                    }
                }
                Column( modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center ) {
                    Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End ) {
                        Image(
                            painter = painterResource(id = R.drawable.preferences__state_enabled),
                            contentDescription = "settings",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { /*TODO*/ }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview() {
    BadgerMe_JetpackTheme {
        BadgerEventsView( null )
    }
}