package com.redbadger.badgerme_jetpack.ui.home.badgers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.ui.BottomBar
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme

@Composable
fun BadgersView(navHostController: NavHostController?, userId: String, authToken: String) {
    Surface {
        Row {
            Text(text = "<PLACEHOLDER - BADGERS>", style = MaterialTheme.typography.h1)
        }
//        Row {
//            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
//                BottomBar(activeTab = 1, navHostController, userId, authToken)
//            }
//        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BadgersPreview() {
    BadgerMe_JetpackTheme {
        BadgersView(
            null,
            "",
            ""
        )
    }
}