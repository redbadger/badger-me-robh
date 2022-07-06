package com.redbadger.badgerme_jetpack.ui.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.util.BadgerEvent
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.BadgerUser
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.WeekFields
import java.util.*

@Composable
fun EventsList(events: List<BadgerEvent>, currentUser: BadgerUser, viewModel: EventsViewModel) {
    Column(modifier = Modifier
        .fillMaxHeight()) {
        if (events.isEmpty()) {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.property_1_sweat),
                        contentDescription = "Sweat emoji"
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Nothing to see here...", style = MaterialTheme.typography.button)
                }
            }
        }
        else {
            val currentWeek = LocalDate
                .now()
                .get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())

            LazyColumn(verticalArrangement = Arrangement.Top) {
                itemsIndexed(events) { index, event ->
                    if (index == 0) Spacer(modifier = Modifier.padding(top = 16.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Column {
                            Column(verticalArrangement = Arrangement.Top) {
                                if (LocalDateTime.parse(event.startTime).toLocalDate()
                                        .isEqual(LocalDate.now().plusDays(1))
                                ) {
                                    if (viewModel.timeFilter.value != "Today" && viewModel.tomorrow.value) {
                                        Text(
                                            modifier = Modifier.padding(top = 8.dp),
                                            text = "Tomorrow",
                                            style = MaterialTheme.typography.h4
                                        )
                                    }
                                    viewModel.tomorrow.value = true
                                    Row(Modifier.padding(top = 8.dp)) {
                                        EventCard(event, currentUser)
                                    }
                                }
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Column {
                            Column(verticalArrangement = Arrangement.Top) {
                                val eventWeek = LocalDateTime
                                    .parse(event.startTime)
                                    .toLocalDate()
                                    .get(
                                        WeekFields.of(Locale.getDefault())
                                            .weekOfWeekBasedYear()
                                    )
                                if (eventWeek == currentWeek && !LocalDateTime
                                        .parse(event.startTime).toLocalDate()
                                        .isEqual(LocalDate.now().plusDays(1))
                                ) {
                                    if (viewModel.timeFilter.value != "Today" && viewModel.thisWeek.value) {
                                        Text(
                                            modifier = Modifier.padding(top = 8.dp),
                                            text = "This week",
                                            style = MaterialTheme.typography.h4
                                        )
                                    }
                                    viewModel.thisWeek.value = true
                                    Row(Modifier.padding(top = 8.dp)) {
                                        EventCard(event, currentUser)
                                    }
                                }
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Column {
                            Column(verticalArrangement = Arrangement.Top) {
                                val eventWeek = LocalDateTime
                                    .parse(event.startTime)
                                    .toLocalDate()
                                    .get(
                                        WeekFields.of(Locale.getDefault())
                                            .weekOfWeekBasedYear()
                                    )
                                if (eventWeek == currentWeek + 1) {
                                    if (viewModel.timeFilter.value != "Today" && viewModel.nextWeek.value) {
                                        Text(
                                            modifier = Modifier.padding(top = 8.dp),
                                            text = "Next week",
                                            style = MaterialTheme.typography.h4
                                        )
                                    }
                                    viewModel.nextWeek.value = true
                                    Row(Modifier.padding(top = 8.dp)) {
                                        EventCard(event, currentUser)
                                    }
                                }
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Column {
                            Column(verticalArrangement = Arrangement.Top) {
                                val eventWeek = LocalDateTime
                                    .parse(event.startTime)
                                    .toLocalDate()
                                    .get(
                                        WeekFields.of(Locale.getDefault())
                                            .weekOfWeekBasedYear()
                                    )
                                if (eventWeek > currentWeek + 1) {
                                    if (viewModel.timeFilter.value != "Today" && viewModel.later.value) {
                                        Text(
                                            modifier = Modifier.padding(top = 8.dp),
                                            text = "Later",
                                            style = MaterialTheme.typography.h4
                                        )
                                    }
                                    viewModel.later.value = true
                                    Row(Modifier.padding(top = 8.dp)) {
                                        EventCard(event, currentUser)
                                    }
                                }
                            }
                        }
                    }
                    if (index == events.lastIndex) Spacer(modifier = Modifier.padding(top = 16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsListPreview() {
    BadgerMe_JetpackTheme {
        EventsList(
            listOf(
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00"
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00"
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00"
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
            ),
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            viewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyEventsListPreview() {
    BadgerMe_JetpackTheme {
        EventsList(
            listOf(),
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            viewModel()
        )
    }
}