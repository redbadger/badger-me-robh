package com.redbadger.badgerme_jetpack.ui.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.navigation.Screen
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.util.BadgerApiInterface
import com.redbadger.badgerme_jetpack.util.RetrofitHelper
import kotlinx.coroutines.*

@Composable
fun InterestSetupView(
    navHostController: NavHostController?,
    userId: String,
    authToken: String,
    viewModel: InterestSetupViewModel = viewModel()
) {
    val snackbarScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val loaded = remember { mutableStateOf(false) }

    if(!loaded.value) {
        viewModel.getInterests(authToken, loaded)
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
        Box {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "What are you interested in?",
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MultiselectInterest(
                        painter = painterResource(id = R.drawable.property_1_food),
                        name = "Food",
                        ticked = viewModel.interests["Food"]?.interested!!
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    MultiselectInterest(
                        painter = painterResource(id = R.drawable.property_1_drinks),
                        name = "Drinks",
                        ticked = viewModel.interests["Drinks"]?.interested!!
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
                        ticked = viewModel.interests["Coffee"]?.interested!!
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    MultiselectInterest(
                        painter = painterResource(id = R.drawable.property_1_chats),
                        name = "Chats",
                        ticked = viewModel.interests["Chats"]?.interested!!
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
                        ticked = viewModel.interests["Walks"]?.interested!!
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    MultiselectInterest(
                        painter = painterResource(id = R.drawable.property_1_hugs),
                        name = "Hugs",
                        ticked = viewModel.interests["Hugs"]?.interested!!
                    )
                }
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        shape = RoundedCornerShape(size = 60.dp),
                        onClick = {
                            updateInterests(
                                authToken,
                                userId,
                                viewModel.interests,
                                snackbarHostState,
                                snackbarScope,
                                navHostController
                            )
                        },
                        enabled = viewModel.interests["Food"]?.interested!!.value
                                or viewModel.interests["Drinks"]?.interested!!.value
                                or viewModel.interests["Coffee"]?.interested!!.value
                                or viewModel.interests["Chats"]?.interested!!.value
                                or viewModel.interests["Walks"]?.interested!!.value
                                or viewModel.interests["Hugs"]?.interested!!.value,
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

fun updateInterests(
    authToken: String,
    userId: String,
    interests: MutableMap<String, InterestSetupViewModel.TrackedInterest>,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    navHostController: NavHostController?) {
    val badgerApi = RetrofitHelper.getInstance().create(BadgerApiInterface::class.java)
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarScope.launch {
            onError(snackbarHostState, "Exception: ${throwable.localizedMessage}")
        }
    }
    val userInterests = mutableListOf<String>()
    interests.forEach {
        if (it.value.interested.value) {
            userInterests.add(it.value.interestId)
        }
    }
    CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = badgerApi.updateUserInterests(authToken, userId, userInterests)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                navHostController?.navigate("${Screen.ProfileSetup.route}/${userId}/${authToken}")
            }
            else {
                onError(snackbarHostState, "ERROR: ${response.code()}")
            }
        }
    }
}

private suspend fun onError(snackbarHostState: SnackbarHostState, errorMessage: String) {
    snackbarHostState.showSnackbar(errorMessage)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BadgerMe_JetpackTheme {
        InterestSetupView(null, "1", "token")
    }
}