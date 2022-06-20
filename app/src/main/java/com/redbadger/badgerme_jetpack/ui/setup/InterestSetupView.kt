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
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme

@Composable
fun InterestSetupView(navHostController: NavHostController?) {
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
                    name = "Food"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                MultiselectInterest(
                    painter = painterResource(id = R.drawable.property_1_drinks),
                    name = "Drinks"
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                MultiselectInterest(
                    painter = painterResource(id = R.drawable.property_1_coffee),
                    name = "Coffee"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                MultiselectInterest(
                    painter = painterResource(id = R.drawable.property_1_chats),
                    name = "Chats"
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                MultiselectInterest(
                    painter = painterResource(id = R.drawable.property_1_walks),
                    name = "Walks"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                MultiselectInterest(
                    painter = painterResource(id = R.drawable.property_1_hugs),
                    name = "Hugs"
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    shape = RoundedCornerShape(size = 60.dp),
                    onClick = {
                        /* TODO */
                    },
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
fun MultiselectInterest(painter: Painter, name: String) {
    val isCheck = remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .width(112.dp)
            .height(116.dp)
            .clickable { isCheck.value = !isCheck.value }
    ){
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
                            horizontalArrangement = Arrangement.End) {
                            Box(modifier = Modifier.size(16.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.control_background),
                                    contentDescription = "tickbox",
                                    modifier = Modifier.size(16.dp)
                                )
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center) {
                                    Column(modifier = Modifier.fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center) {
                                        if (isCheck.value) {
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