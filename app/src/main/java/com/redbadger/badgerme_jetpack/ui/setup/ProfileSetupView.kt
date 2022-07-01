package com.redbadger.badgerme_jetpack.ui.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.navigation.Screen
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme

@Composable
fun ProfileSetupView(
    navHostController: NavHostController?,
    userId: String,
    viewModel: ProfileSetupViewModel = viewModel()
) {
    val bio = remember { mutableStateOf("") }

    if(viewModel.user.value.id.isNullOrBlank()) {
        viewModel.getUser(userId)
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
                        text = "Tell us about yourself",
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(
                            start = 40.dp,
                            end = 40.dp,
                            top = 48.dp,
                            bottom = 16.dp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.avatar_human),
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(96.dp)
                    )
                }
                Column(Modifier.padding(horizontal = 40.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Name",
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(top = 40.dp, bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextField(
                            value = "${viewModel.user.value.first_name.replaceFirstChar { it.uppercase() }} ${viewModel.user.value.last_name.replaceFirstChar { it.uppercase() }}",
                            onValueChange = {},
                            placeholder = { Text("Write something") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "About Me",
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextField(
                            value = bio.value,
                            onValueChange = { if (it.length <= 200) {bio.value = it} },
                            placeholder = { Text("For example: I like Piña Coladas in the rain") },
                            modifier = Modifier.height(180.dp).fillMaxWidth()
                        )
                    }
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
                          /* TODO: There doesn't actually seem to be an endpoint to send this info to */
                            navHostController?.navigate(
                                "${Screen.Events.route}/${userId}"
                            )
                        },
                        enabled = bio.value.isNotBlank(),
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview() {
    BadgerMe_JetpackTheme {
        ProfileSetupView( null, "1" )
    }
}