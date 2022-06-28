package com.redbadger.badgerme_jetpack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.redbadger.badgerme_jetpack.ui.login.LoginView
import com.redbadger.badgerme_jetpack.ui.setup.InterestSetupView
import com.redbadger.badgerme_jetpack.ui.setup.ProfileSetupView
import com.redbadger.badgerme_jetpack.ui.splash.SplashView

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Splash.route) { SplashView(navController)}
        composable(Screen.Login.route) { LoginView(navController) }
        composable(Screen.InterestsSetup.route + "/{userId}") { backStackEntry ->
            InterestSetupView(navController, backStackEntry.arguments?.getString("userId"))
         }
        composable(Screen.ProfileSetup.route + "/{userId}") { ProfileSetupView(navController)}
    }
}

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Login: Screen("login")
    object InterestsSetup: Screen("setup/interests")
    object ProfileSetup: Screen("setup/profile")
}