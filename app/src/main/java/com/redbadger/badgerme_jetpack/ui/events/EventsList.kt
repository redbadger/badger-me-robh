package com.redbadger.badgerme_jetpack.ui.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.util.BadgerEvent
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.BadgerUser

@Composable
fun EventsList(events: List<BadgerEvent>, currentUser: BadgerUser) {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
        if (events.isEmpty()) {
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
        else {
            LazyColumn(Modifier.fillMaxHeight(),verticalArrangement = Arrangement.Top) {
                items(events) { event ->
                    Row (Modifier.padding(top = 8.dp)) {
                        EventCard(event, currentUser)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EventsListPreview() {
    BadgerMe_JetpackTheme {
        EventsList(
            listOf(
                BadgerEvent("Test event 1",
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
                    "Test Event 2",
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
                    "Test Event 3",
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
                )
            ),
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            )
        )
    }
}

@Preview
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
            )
        )
    }
}