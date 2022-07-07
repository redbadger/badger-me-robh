package com.redbadger.badgerme_jetpack.ui.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.setup.MultiselectInterest
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.ui.theme.uiLightest
import com.redbadger.badgerme_jetpack.util.BadgerEvent
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.BadgerUser
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BadgerEventsView(
    navHostController: NavHostController?,
    userId: String?,
    authToken: String,
    viewModel: EventsViewModel = viewModel()
) {
    val tabs = listOf("Today", "Upcoming")
    val currentTab = remember { mutableStateOf(0) }
    val interests = listOf<BadgerInterest>()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    val food = remember { mutableStateOf(false) }
    val drinks = remember { mutableStateOf(false) }
    val coffee = remember { mutableStateOf(false) }
    val chats = remember { mutableStateOf(false) }
    val walks = remember { mutableStateOf(false) }
    val hugs = remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(8.dp),
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .background(color = uiLightest)
            ) {
                Column {
                    Box (Modifier.background(Color.White)) {
                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Start) {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.controls_close
                                ),
                                contentDescription = "settings",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                            )
                        }
                        Row(Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "Filter by interests (${interests.size})",
                                style = MaterialTheme.typography.h5,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MultiselectInterest(
                            painter = painterResource(id = R.drawable.property_1_food),
                            name = "Food",
                            ticked = food
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        MultiselectInterest(
                            painter = painterResource(id = R.drawable.property_1_drinks),
                            name = "Drinks",
                            ticked = drinks
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MultiselectInterest(
                            painter = painterResource(id = R.drawable.property_1_coffee),
                            name = "Coffee",
                            ticked = coffee
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        MultiselectInterest(
                            painter = painterResource(id = R.drawable.property_1_chats),
                            name = "Chats",
                            ticked = chats
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MultiselectInterest(
                            painter = painterResource(id = R.drawable.property_1_walks),
                            name = "Walks",
                            ticked = walks
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        MultiselectInterest(
                            painter = painterResource(id = R.drawable.property_1_hugs),
                            name = "Hugs",
                            ticked = hugs
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 24.dp))
                }
            }
        }, sheetPeekHeight = 0.dp
    ) {
        Box {
            Column() {
                Row(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier
                            .background(color = Color.White)
                            .padding(start = 16.dp, top = 7.5.dp, end = 16.dp)
                    ) {
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
                                            .clickable {
                                                coroutineScope.launch {
                                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
                Row {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
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