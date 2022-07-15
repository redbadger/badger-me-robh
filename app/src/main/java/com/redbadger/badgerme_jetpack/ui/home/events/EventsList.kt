package com.redbadger.badgerme_jetpack.ui.home.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import java.time.OffsetDateTime
import java.time.temporal.WeekFields
import java.util.*

@Composable
fun EventsList(
    error: Boolean,
    events: List<BadgerEvent>,
    currentUser: BadgerUser,
    viewModel: EventsViewModel,
    scrollState: LazyListState
) {
    Column {
        if (error) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.property_1_grimace),
                        contentDescription = "Grimace emoji"
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Ohhh bugger something went wrong",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
        else {
            if (events.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
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
                        Text(
                            text = "Nothing to see here...",
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            } else {
                val currentWeek = LocalDate
                    .now()
                    .get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())

                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    state = scrollState,
                    contentPadding = PaddingValues(top = 75.dp)
                ) {
                    itemsIndexed(events) { index, event ->
                        if (index == 0) Spacer(modifier = Modifier.padding(top = 16.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                        Tomorrow
                            Column {
                                Column(verticalArrangement = Arrangement.Top) {
                                    if (OffsetDateTime.parse(event.startTime).toLocalDate()
                                            .isEqual(LocalDate.now().plusDays(1))
                                    ) {
                                        if (viewModel.tomorrow.value == -1) viewModel.tomorrow.value =
                                            index
                                        if (viewModel.timeFilter.value != "Today"
                                            && viewModel.tomorrow.value == index
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(top = 8.dp),
                                                text = "Tomorrow",
                                                style = MaterialTheme.typography.h4
                                            )
                                        }
                                        Row(Modifier.padding(top = 8.dp)) {
                                            EventCard(event, currentUser)
                                        }
                                    }
                                }
                            }
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                         This week
                            Column {
                                Column(verticalArrangement = Arrangement.Top) {
                                    val eventWeek = OffsetDateTime
                                        .parse(event.startTime)
                                        .toLocalDate()
                                        .get(
                                            WeekFields.of(Locale.getDefault())
                                                .weekOfWeekBasedYear()
                                        )
                                    if (eventWeek == currentWeek && !OffsetDateTime
                                            .parse(event.startTime).toLocalDate()
                                            .isEqual(LocalDate.now().plusDays(1))
                                    ) {
                                        if (viewModel.thisWeek.value == -1) viewModel.thisWeek.value =
                                            index
                                        if (viewModel.timeFilter.value != "Today" && viewModel.thisWeek.value == index) {
                                            Text(
                                                modifier = Modifier.padding(top = 8.dp),
                                                text = "This week",
                                                style = MaterialTheme.typography.h4
                                            )
                                        }
                                        Row(Modifier.padding(top = 8.dp)) {
                                            EventCard(event, currentUser)
                                        }
                                    }
                                }
                            }
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                        Next week
                            Column {
                                Column(verticalArrangement = Arrangement.Top) {
                                    val eventWeek = OffsetDateTime
                                        .parse(event.startTime)
                                        .toLocalDate()
                                        .get(
                                            WeekFields.of(Locale.getDefault())
                                                .weekOfWeekBasedYear()
                                        )
                                    if (eventWeek == currentWeek + 1) {
                                        if (viewModel.nextWeek.value == -1) viewModel.nextWeek.value =
                                            index
                                        if (viewModel.timeFilter.value != "Today" && viewModel.nextWeek.value == index) {
                                            Text(
                                                modifier = Modifier.padding(top = 8.dp),
                                                text = "Next week",
                                                style = MaterialTheme.typography.h4
                                            )
                                        }
                                        Row(Modifier.padding(top = 8.dp)) {
                                            EventCard(event, currentUser)
                                        }
                                    }
                                }
                            }
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                        Later
                            Column {
                                Column(verticalArrangement = Arrangement.Top) {
                                    val eventWeek = OffsetDateTime
                                        .parse(event.startTime)
                                        .toLocalDate()
                                        .get(
                                            WeekFields.of(Locale.getDefault())
                                                .weekOfWeekBasedYear()
                                        )
                                    if (eventWeek > currentWeek + 1) {
                                        if (viewModel.later.value == -1) viewModel.later.value =
                                            index
                                        if (viewModel.timeFilter.value != "Today" && viewModel.later.value == index) {
                                            Text(
                                                modifier = Modifier.padding(top = 8.dp),
                                                text = "Later",
                                                style = MaterialTheme.typography.h4
                                            )
                                        }
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
}

@Preview(showBackground = true)
@Composable
fun EventsListPreview() {
    BadgerMe_JetpackTheme {
        EventsList(
            false,
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
                    "2022-06-20T16:00:00","2022-07-20T19:00:00", "Somewhere"
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00", "Somewhere"
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
                    "2022-07-06T16:00:00","2022-07-07T19:00:00", "Somewhere"
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00", "Somewhere"
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00", "Somewhere"
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00", "Somewhere"
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
                    "2022-07-20T12:00:00","2022-07-20T15:00:00", "Somewhere"
                )
            ),
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            viewModel(),
            rememberLazyListState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyEventsListPreview() {
    BadgerMe_JetpackTheme {
        EventsList(
            false,
            listOf(),
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            viewModel(),
            rememberLazyListState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorEventsListPreview() {
    BadgerMe_JetpackTheme {
        EventsList(
            true,
            listOf(),
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            viewModel(),
            rememberLazyListState()
        )
    }
}