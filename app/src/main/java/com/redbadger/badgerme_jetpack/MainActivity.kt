package com.redbadger.badgerme_jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.redbadger.badgerme_jetpack.navigation.NavigationHost
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BadgerMe_JetpackTheme {
               NavigationHost()
            }
        }
    }
}