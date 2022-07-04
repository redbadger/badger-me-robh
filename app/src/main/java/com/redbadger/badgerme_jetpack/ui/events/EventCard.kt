package com.redbadger.badgerme_jetpack.ui.events

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.BadgerUser

@Composable
fun EventCard(
    eventName: String,
    eventOwner: BadgerUser,
    attendees: List<BadgerUser>,
    tags: List<BadgerInterest>
) {
    val attendanceList = "${eventOwner.first_name} ${eventOwner.last_name} " +
            if (attendees.isNotEmpty()) "& ${attendees.size} others are going"
            else "is going"
    Card(elevation = 4.dp, modifier = Modifier.width(343.dp)) {
        Surface(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.Start) {
                Column {
                    Row(modifier = Modifier.padding(bottom = 2.dp)) {
                        Text(
                            text = eventName,
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
                        ChipGroup(tags)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.End) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.activity_food),
                        contentDescription = "Event tag icon"
                    )
                }
            }
        }
    }
}

@Composable
fun Chip(name: String, isSelected: Boolean = false, onSelectionChanged: (String) -> Unit = {}) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colors.primary else Color.LightGray
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
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun ChipGroup(
    tags: List<BadgerInterest>,
    selectedTag: BadgerInterest? = null,
    onSelectedChanged: (String) -> Unit = {}
) {
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
        EventCard(
            "Test Event",
            BadgerUser("1", "Hugh", "Mann", "hugh.mann@red-badger.com"),
            listOf(
                BadgerUser("2", "Guy", "Fellow", "guy.fellow@red-badger.com"),
                BadgerUser("3", "Andy", "Noether", "andy.noether@red-badger.com")
            ),
            listOf(BadgerInterest("1", "Food"))
        )
    }
}