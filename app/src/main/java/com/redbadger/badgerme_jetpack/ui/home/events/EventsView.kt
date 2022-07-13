package com.redbadger.badgerme_jetpack.ui.home.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.BottomBar
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.util.BadgerEvent
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.BadgerUser
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun BadgerEventsView(
    navHostController: NavHostController?,
    userId: String?,
    authToken: String,
    viewModel: EventsViewModel = viewModel()
) {
    val tabs = listOf("Today", "Upcoming")
    val currentTab = remember { mutableStateOf(0) }
    Box {
        Column () {
            Row(Modifier.fillMaxWidth()) {
                Column(
                    Modifier
                        .background(color = Color.White)
                        .padding(start = 16.dp, top = 7.5.dp, end = 16.dp)) {
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
                            selectedTabIndex = currentTab.value,
                            backgroundColor = Color.White,
                            contentColor = MaterialTheme.colors.primary,
                            modifier = Modifier.width(210.dp)
                        ) {
                            tabs.forEachIndexed { index, it ->
                                Tab(
                                    selected = true,
                                    onClick = {
                                        viewModel.timeFilter.value = it
                                        currentTab.value = index
                                    },
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
            Box {
                Row {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row {
                            EventsList(
                                getEvents().filter {
                                    when (viewModel.timeFilter.value) {
                                        "Today" -> {
                                            viewModel.tomorrow.value = -1
                                            viewModel.thisWeek.value = -1
                                            viewModel.nextWeek.value = -1
                                            viewModel.later.value = -1

                                            LocalDateTime
                                                .parse(it.startTime).toLocalDate()
                                                .isEqual(LocalDate.now())
                                        }
                                        "Upcoming" -> {
                                            viewModel.tomorrow.value = -1
                                            viewModel.thisWeek.value = -1
                                            viewModel.nextWeek.value = -1
                                            viewModel.later.value = -1

                                            LocalDateTime
                                                .parse(it.startTime).toLocalDate()
                                                .isAfter(LocalDate.now())
                                        }
                                        else -> false
                                    }
                                }.sortedBy { it.startTime },
                                BadgerUser(
                                    "1",
                                    "Hugh",
                                    "Mann",
                                    "hugh.mann@red-badger.com"
                                ),
                                viewModel
                            )
                        }
                    }
                }
//                Row {
//                    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
//                        BottomBar(activeTab = 0, navHostController, userId, authToken)
//                    }
//                }
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
            listOf(BadgerInterest("1", "Food")),
            "2022-06-20T16:00:00","2022-07-20T19:00:00"
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
            listOf(BadgerInterest("1", "Coffee")),
            "2022-07-07T12:00:00","2022-07-20T15:00:00"
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
            ),
            "2022-07-06T16:00:00","2022-07-07T19:00:00"
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
            listOf(BadgerInterest("1", "Walks")),
            "2022-07-20T12:00:00","2022-07-20T15:00:00"
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
            listOf(BadgerInterest("1", "Drinks")),
            "2022-07-09T12:00:00","2022-07-20T15:00:00"
        ),
        BadgerEvent("Hugs event",
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            listOf(),
            listOf(BadgerInterest("1", "Hugs")),
            "2022-07-11T12:00:00","2022-07-20T15:00:00"
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
            listOf(BadgerInterest("1", "Chats")),
            "2022-07-20T12:00:00","2022-07-20T15:00:00"
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