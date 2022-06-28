package com.redbadger.badgerme_jetpack.ui.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.navigation.Screen
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme

@Composable
fun InterestSetupView(navHostController: NavHostController?, userId: String?) {
    val snackbarScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val food = remember { mutableStateOf(false) }
    val drinks = remember { mutableStateOf(false) }
    val coffee = remember { mutableStateOf(false) }
    val chats = remember { mutableStateOf(false) }
    val walks = remember { mutableStateOf(false) }
    val hugs = remember { mutableStateOf(false) }

    Box {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "What are you interested in?",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    shape = RoundedCornerShape(size = 60.dp),
                    onClick = {
                        /* TODO */
                        val interests = mapOf(
                            "food" to food,
                            "drinks" to drinks,
                            "coffee" to coffee,
                            "chats" to chats,
                            "walks" to walks,
                            "hugs" to hugs
                        )
                        navHostController?.navigate(Screen.ProfileSetup.route)
                    },
                    enabled = food.value or drinks.value or coffee.value
                            or chats.value or walks.value or hugs.value,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text(
                        text = "Continue",
                        modifier = Modifier.padding(vertical = 10.dp),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@Composable
fun MultiselectInterest(painter: Painter, name: String, ticked: MutableState<Boolean>) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .width(112.dp)
            .height(116.dp)
            .clickable { ticked.value = !ticked.value }
    ) {
        Column {
            Row {
                Box {
                    Image(
                        painter = painter,
                        contentDescription = "%s interest icon".format(name)
                    )
                    Column(verticalArrangement = Arrangement.Top) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Box(modifier = Modifier.size(16.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.control_background),
                                    contentDescription = "tickbox",
                                    modifier = Modifier.size(16.dp)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        if (ticked.value) {
                                            Image(
                                                painter = painterResource(id = R.drawable.vector_13__stroke_),
                                                contentDescription = "tickmark",
                                                modifier = Modifier.size(10.67.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BadgerMe_JetpackTheme {
        InterestSetupView(null)
    }
}