package com.redbadger.badgerme_jetpack.ui.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.redbadger.badgerme_jetpack.util.BadgerEvent
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.BadgerUser

@Composable
fun BadgerEventsView(navHostController: NavHostController?, userId: String?, authToken: String) {
    val tabs = listOf("Today", "Upcoming")
    Box {
        Column () {
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.background(color = Color.White).padding(start = 16.dp, top = 7.5.dp, end = 16.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Badger events",
                            style = MaterialTheme.typography.h3
                        )
                    }
                    Row(
                        modifier = Modifier
                            .height(48.dp)
                            .background(color = Color.White)
                            .fillMaxWidth()
                    ) {
                        TabRow(
                            selectedTabIndex = 0,
                            backgroundColor = Color.White,
                            contentColor = MaterialTheme.colors.primary,
                            modifier = Modifier.width(210.dp)
                        ) {
                            tabs.forEachIndexed { _, it ->
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
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.preferences__state_enabled
                                    ),
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
            Row {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    EventsList(getEvents(), BadgerUser(
                        "1",
                        "Hugh",
                        "Mann",
                        "hugh.mann@red-badger.com"
                        )
                    )
                }
            }
        }
    }
}

fun getEvents(): List<BadgerEvent> {
    return listOf(
        BadgerEvent("Food event",
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            listOf(
                BadgerUser(
                    "2",
                    "Guy",
                    "Fellow",
                    "guy.fellow@red-badger.com"
                ),
                BadgerUser(
                    "3",
                    "Andy",
                    "Noether",
                    "andy.noether@red-badger.com"
                )
            ),
            listOf(BadgerInterest("1", "Food"))
        ),
        BadgerEvent(
            "Coffee event",
            BadgerUser(
                "2",
                "Guy",
                "Fellow",
                "guy.fellow@red-badger.com"
            ),
            listOf(),
            listOf(BadgerInterest("1", "Coffee"))
        ),
        BadgerEvent(
            "Mixed event",
            BadgerUser(
                "2",
                "Guy",
                "Fellow",
                "guy.fellow@red-badger.com"
            ),
            listOf(
                BadgerUser(
                    "1",
                    "Hugh",
                    "Mann",
                    "hugh.mann@red-badger.com"
                ),
                BadgerUser(
                    "3",
                    "Andy",
                    "Noether",
                    "andy.noether@red-badger.com"
                )
            ),
            listOf(
                BadgerInterest("1","Food" ),
                BadgerInterest("2", "Walks" )
            )
        ),
        BadgerEvent("Walking event",
            BadgerUser(
                "3",
                "Andy",
                "Noether",
                "andy.noether@red-badger.com"
            ),
            listOf(
                BadgerUser(
                    "2",
                    "Guy",
                    "Fellow",
                    "guy.fellow@red-badger.com"
                ),
                BadgerUser(
                    "3",
                    "Andy",
                    "Noether",
                    "andy.noether@red-badger.com"
                ),
                BadgerUser(
                    "1",
                    "Hugh",
                    "Mann",
                    "hugh.mann@red-badger.com"
                )
            ),
            listOf(BadgerInterest("1", "Walks"))
        ),
        BadgerEvent("Drinking event",
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            listOf(
                BadgerUser(
                    "2",
                    "Guy",
                    "Fellow",
                    "guy.fellow@red-badger.com"
                ),
                BadgerUser(
                    "3",
                    "Andy",
                    "Noether",
                    "andy.noether@red-badger.com"
                )
            ),
            listOf(BadgerInterest("1", "Drinks"))
        ),
        BadgerEvent("Hugs event",
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            listOf(),
            listOf(BadgerInterest("1", "Hugs"))
        ),
        BadgerEvent("Chats event",
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            listOf(
                BadgerUser(
                    "2",
                    "Guy",
                    "Fellow",
                    "guy.fellow@red-badger.com"
                ),
                BadgerUser(
                    "3",
                    "Andy",
                    "Noether",
                    "andy.noether@red-badger.com"
                )
            ),
            listOf(BadgerInterest("1", "Chats"))
        )
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyPreview() {
    BadgerMe_JetpackTheme {
        BadgerEventsView(null, "1", "token")
    }
}