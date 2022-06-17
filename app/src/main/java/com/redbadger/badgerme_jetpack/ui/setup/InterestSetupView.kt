package com.redbadger.badgerme_jetpack.ui.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
    Card(elevation = 4.dp, modifier = Modifier.width(112.dp).height(116.dp)) {
        Column {
            Row {
                Box {
                    Image(painter = painter, contentDescription = "%s interest icon".format(name))
                    Column(modifier = Modifier.fillMaxHeight(),verticalArrangement = Arrangement.Top) {
                        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                            Checkbox(checked = false, onCheckedChange = {
                                /* TODO */
                            })
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