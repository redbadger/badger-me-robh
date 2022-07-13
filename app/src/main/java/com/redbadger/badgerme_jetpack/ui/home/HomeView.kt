package com.redbadger.badgerme_jetpack.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.redbadger.badgerme_jetpack.ui.BottomBar
import com.redbadger.badgerme_jetpack.ui.home.activities.ActivitiesView
import com.redbadger.badgerme_jetpack.ui.home.badgers.BadgersView
import com.redbadger.badgerme_jetpack.ui.home.events.BadgerEventsView
import com.redbadger.badgerme_jetpack.ui.home.profile.ProfileView
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme


@Composable
fun HomeView(navHostController: NavHostController?, userId: String, authToken: String) {
    val activeTab = remember { mutableStateOf(0) }
    Surface {
        Row {
            when (activeTab.value) {
                0 -> BadgerEventsView(navHostController, userId, authToken)
                1 -> BadgersView(navHostController, userId, authToken)
                2 -> ProfileView(navHostController, userId, authToken)
                3 -> ActivitiesView(navHostController, userId, authToken)
            }
        }
        Row {
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
                BottomBar(activeTab = activeTab, navHostController, userId, authToken)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BadgersPreview() {
    BadgerMe_JetpackTheme {
        HomeView(
            null,
            "",
            ""
        )
    }
}