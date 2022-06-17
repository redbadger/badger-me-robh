package com.redbadger.badgerme_jetpack.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.navigation.Screen
import com.redbadger.badgerme_jetpack.ui.Title
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme

@Composable
fun SplashView(navHostController: NavHostController?) {
    Box {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 300.dp), horizontalArrangement = Arrangement.Center) {
                Title()
            }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.type_minimal),
                    contentDescription = "Logo",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 300.dp)
                .size(50.dp), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Loading", style = MaterialTheme.typography.subtitle1)
            }
        }
    }

    Thread.sleep(10000)
    navHostController?.navigate(Screen.Login.route)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BadgerMe_JetpackTheme {
        SplashView(null)
    }
}