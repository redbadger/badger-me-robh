package com.redbadger.badgerme_jetpack.ui.home.events

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.OffsetDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BadgerEventsView(
    navHostController: NavHostController?,
    userId: String,
    authToken: String,
    viewModel: EventsViewModel = viewModel()
) {
    val loaded = remember { mutableStateOf(false) }
    val interests = listOf<BadgerInterest>()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val modalContent = remember { mutableStateOf("filter") }
    val scrollState = rememberLazyListState()
    val scrollUpState = viewModel.scrollUp.observeAsState()

    viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(8.dp),
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(
                        when (modalContent.value) {
                            "filter" -> 520.dp
                            "add" -> 700.dp
                            else -> 220.dp
                        }
                    )
                    .background(color = uiLightest)
            ) {
                Column {
                    Box(Modifier.background(Color.White)) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.controls_close
                                ),
                                contentDescription = "settings",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        if (modalContent.value == "filter") {
                                            loaded.value = false
                                            viewModel.getActivities(authToken, loaded, userId)
                                        }
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                            )
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text =  when (modalContent.value) {
                                    "filter" -> "Filter by interests (${interests.size})"
                                    "add" -> "Create Event"
                                    else -> "ERROR UNKNOWN MODAL TYPE"
                                },
                                style = MaterialTheme.typography.h5,
                            )
                        }
                        if (modalContent.value == "add") {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = { /*TODO: Submit new event to API*/ },
                                    enabled = false
                                ) {
                                    Text(
                                        text = "Done",
                                        style = MaterialTheme.typography.h5,
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 24.dp))
                    if (modalContent.value == "filter") FilterSelect(viewModel)
                    else if (modalContent.value == "add") AddEvent(viewModel)
                }
            }
        }, sheetPeekHeight = 0.dp
    ) {
        Box {
            if(!loaded.value) {
                viewModel.getActivities(authToken, loaded, userId)
                Box {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
            else {
                EventsList(
                    viewModel.error.value,
                    viewModel.activities
                        .filter {
//                                Filter by time
                            when (viewModel.timeFilter.value) {
                                "Today" -> {
                                    viewModel.tomorrow.value = -1
                                    viewModel.thisWeek.value = -1
                                    viewModel.nextWeek.value = -1
                                    viewModel.later.value = -1

                                    OffsetDateTime
                                        .parse(it.startTime).toLocalDate()
                                        .isEqual(LocalDate.now())
                                }
                                "Upcoming" -> {
                                    viewModel.tomorrow.value = -1
                                    viewModel.thisWeek.value = -1
                                    viewModel.nextWeek.value = -1
                                    viewModel.later.value = -1

                                    OffsetDateTime
                                        .parse(it.startTime).toLocalDate()
                                        .isAfter(LocalDate.now())
                                }
                                else -> false
                            }
                        }
                        .filter {
//                                Filter by interest
                            (viewModel.food.value && it.tags.contains(
                                viewModel.interests["Food"]
                            )) ||
                            (viewModel.drinks.value && it.tags.contains(
                                viewModel.interests["Drinks"]
                            )) ||
                            (viewModel.coffee.value && it.tags.contains(
                                viewModel.interests["Coffee"]
                            )) ||
                            (viewModel.chats.value && it.tags.contains(
                                viewModel.interests["Chats"]
                            )) ||
                            (viewModel.walks.value && it.tags.contains(
                                viewModel.interests["Walks"]
                            )) ||
                            (viewModel.hugs.value && it.tags.contains(
                                viewModel.interests["Hugs"]
                            ))
                        }
                        .sortedBy {
                            it.startTime
                        },
                    viewModel.currentUser.value,
                    viewModel,
                    scrollState
                )
            }
            ScrollableAppBar(
                viewModel,
                scrollUpState,
                coroutineScope,
                modalContent,
                bottomSheetScaffoldState
            )
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(bottom = 14.dp), verticalArrangement = Arrangement.Bottom) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, bottom = 54.dp), horizontalArrangement = Arrangement.End) {
                    FloatingActionButton(
                        onClick = {
                            modalContent.value = "add"
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        },
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Icon(Icons.Filled.Add, "")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScrollableAppBar(
    viewModel: EventsViewModel = viewModel(),
    scrollUpState: State<Boolean?>,
    coroutineScope: CoroutineScope,
    modalContent: MutableState<String>,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    val tabs = listOf("Today", "Upcoming")
    val currentTab = remember { mutableStateOf(0) }

    val position by animateFloatAsState(if (scrollUpState.value == true) -250f else 0f)

    Surface(modifier = Modifier.graphicsLayer { translationY = (position) }, elevation = 8.dp) {
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
                                    viewModel.tomorrow.value = -1
                                    viewModel.thisWeek.value = -1
                                    viewModel.nextWeek.value = -1
                                    viewModel.later.value = -1

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
                                        modalContent.value = "filter"
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
    }
}

@Composable
fun FilterSelect(viewModel: EventsViewModel = viewModel()) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        MultiselectInterest(
            painter = painterResource(id = R.drawable.property_1_food),
            name = "Food",
            ticked = viewModel.food
        )
        Spacer(modifier = Modifier.padding(8.dp))
        MultiselectInterest(
            painter = painterResource(id = R.drawable.property_1_drinks),
            name = "Drinks",
            ticked = viewModel.drinks
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
            ticked = viewModel.coffee
        )
        Spacer(modifier = Modifier.padding(8.dp))
        MultiselectInterest(
            painter = painterResource(id = R.drawable.property_1_chats),
            name = "Chats",
            ticked = viewModel.chats
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
            ticked = viewModel.walks
        )
        Spacer(modifier = Modifier.padding(8.dp))
        MultiselectInterest(
            painter = painterResource(id = R.drawable.property_1_hugs),
            name = "Hugs",
            ticked = viewModel.hugs
        )
    }
    Spacer(modifier = Modifier.padding(top = 24.dp))
}

@Composable
fun AddEvent(viewModel: EventsViewModel = viewModel()){
    Row {
        Text(text = "<Placeholder - Add Event>", style = MaterialTheme.typography.h2)
    }
}

fun getEvents(): List<BadgerEvent> {
    return listOf(
        BadgerEvent(
            "Food event",
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
            listOf(BadgerInterest("0", "Food")),
            OffsetDateTime.now().plusHours(1).toString(),
            OffsetDateTime.now().plusHours(2).toString(),
            "Somewhere"
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
            listOf(BadgerInterest("2", "Coffee")),
            OffsetDateTime.now().plusDays(1).plusHours(1).toString(),
            OffsetDateTime.now().plusDays(1).plusHours(1).toString(),
            "Somewhere"
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
                BadgerInterest("0", "Food"),
                BadgerInterest("4", "Walks")
            ),
            OffsetDateTime.now().plusDays(6).toString(),
            OffsetDateTime.now().plusDays(6).toString(),
            "Somewhere"
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
            "2022-09-20T12:00:00",
            "2022-09-20T15:00:00",
            "Somewhere"
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
            "2022-09-09T12:00:00",
            "2022-09-20T15:00:00",
            "Somewhere"
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
            "2022-09-11T12:00:00",
            "2022-09-20T15:00:00",
            "Somewhere"
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
            "2022-09-20T12:00:00",
            "2022-09-20T15:00:00",
            "Somewhere"
        ),
        BadgerEvent(
            "Walking event",
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
            listOf(BadgerInterest("4", "Walks")),
            OffsetDateTime.now().plusDays(10).toString(),
            OffsetDateTime.now().plusDays(10).toString(),
            "Somewhere"
        ),
        BadgerEvent(
            "Drinking event",
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
            OffsetDateTime.now().plusDays(3).toString(),
            OffsetDateTime.now().plusDays(3).toString(),
            "Somewhere"
        ),
        BadgerEvent(
            "Hugs event",
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            listOf(),
            listOf(BadgerInterest("5", "Hugs")),
            OffsetDateTime.now().plusDays(5).toString(),
            OffsetDateTime.now().plusDays(5).toString(),
            "Somewhere"
        ),
        BadgerEvent(
            "Chats event",
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
            listOf(BadgerInterest("3", "Chats")),
            OffsetDateTime.now().plusDays(14).toString(),
            OffsetDateTime.now().plusDays(14).toString(),
            "Somewhere"
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
            "2022-09-09T12:00:00","2022-09-20T15:00:00", "Somewhere"
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
            "2022-09-11T12:00:00","2022-09-20T15:00:00", "Somewhere"
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
            "2022-09-20T12:00:00","2022-09-20T15:00:00", "Somewhere"
        ),
        BadgerEvent(
            "Walking event",
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
            listOf(BadgerInterest("4", "Walks")),
            OffsetDateTime.now().plusDays(10).toString(),
            OffsetDateTime.now().plusDays(10).toString(),
            "Somewhere"
        ),
        BadgerEvent(
            "Drinking event",
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
            OffsetDateTime.now().plusDays(3).toString(),
            OffsetDateTime.now().plusDays(3).toString(),
            "Somewhere"
        ),
        BadgerEvent(
            "Hugs event",
            BadgerUser(
                "1",
                "Hugh",
                "Mann",
                "hugh.mann@red-badger.com"
            ),
            listOf(),
            listOf(BadgerInterest("5", "Hugs")),
            OffsetDateTime.now().plusDays(5).toString(),
            OffsetDateTime.now().plusDays(5).toString(),
            "Somewhere"
        ),
        BadgerEvent(
            "Chats event",
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
            listOf(BadgerInterest("3", "Chats")),
            OffsetDateTime.now().plusDays(14).toString(),
            OffsetDateTime.now().plusDays(14).toString(),
            "Somewhere"
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