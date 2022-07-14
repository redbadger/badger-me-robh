package com.redbadger.badgerme_jetpack.ui.home.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.util.BadgerEvent
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.BadgerUser

@Composable
fun EventCard(
    event: BadgerEvent,
    currentUser: BadgerUser
) {
    val image = if (event.tags.size == 1) when (event.tags[0].name) {
        "Food" -> R.drawable.activity_food
        "Drinks" -> R.drawable.activity_drinks
        "Chats" -> R.drawable.activity_chats
        "Hugs" -> R.drawable.activity_hugs
        "Walks" -> R.drawable.activity_walking
        "Coffee" -> R.drawable.activity_coffee
        else -> R.drawable.activity_mixed
    } else R.drawable.activity_mixed
    val going = (event.attendees.indexOf(currentUser) > -1) or (event.owner == currentUser)
    val attendanceList = (if (going) "You " else "${event.owner.first_name} ${event.owner.last_name} ") +
            (if (event.attendees.isNotEmpty()) "& ${event.attendees.size} others " else "") +
            (if (going) "are " else "is ") + "going"

    Card(elevation = 4.dp, modifier = Modifier.width(343.dp)) {
        Surface(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.Start) {
                Column {
                    Row(modifier = Modifier.padding(bottom = 2.dp)) {
                        Text(
                            text = event.name,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(
                            text = attendanceList,
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Row (modifier = Modifier.width(239.dp)) {
                        ChipGroup(event.tags, going, event.owner == currentUser)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.End) {
                Column {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = "Event tag icon"
                    )
                }
            }
        }
    }
}

@Composable
fun Chip(
    name: String,
    isSelected: Boolean = false,
    onSelectionChanged: (String) -> Unit = {},
    specifiedColor: Color? = null
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        color = specifiedColor ?: if (isSelected) MaterialTheme.colors.primary else Color.LightGray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun ChipGroup(
    tags: List<BadgerInterest>,
    going: Boolean,
    owner: Boolean
) {
    if (owner) {
        Chip(name = "Owner", specifiedColor = MaterialTheme.colors.primary)
    }
    else if (going) {
        Chip(name = "Going", specifiedColor = MaterialTheme.colors.primaryVariant)
    }
    LazyRow {
        items(tags) { tag ->
            Chip(name = tag.name)
        }
    }
}

@Preview
@Composable
fun PreviewEventCard() {
    BadgerMe_JetpackTheme {
        Column {
            EventCard(
                BadgerEvent(
                    "Test Event",
                    BadgerUser(
                        "1",
                        "Hugh",
                        "Mann",
                        "hugh.mann@red-badger.com",
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
                    "",""
                ),
                BadgerUser(
                    "1",
                    "Hugh",
                    "Mann",
                    "hugh.mann@red-badger.com"
                )
            )
            EventCard(
                BadgerEvent(
                    "Test Event 2",
                    BadgerUser(
                        "2",
                        "Guy",
                        "Fellow",
                        "guy.fellow@red-badger.com"
                    ),
                    listOf(),
                    listOf(BadgerInterest("1", "Coffee")),
                    "",""
                ),
                BadgerUser(
                    "1",
                    "Hugh",
                    "Mann",
                    "hugh.mann@red-badger.com"
                )
            )
            EventCard(
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
                    ),
                    "",""
                ),
                BadgerUser(
                    "1",
                    "Hugh",
                    "Mann",
                    "hugh.mann@red-badger.com"
                )
            )
            EventCard(
                BadgerEvent(
                    "Test Event 3",
                    BadgerUser(
                        "1",
                        "Hugh",
                        "Mann",
                        "hugh.mann@red-badger.com"
                    ),
                    listOf(),
                    listOf(
                        BadgerInterest("1","Food" ),
                        BadgerInterest("2", "Walks" )
                    ),
                    "",""
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
}