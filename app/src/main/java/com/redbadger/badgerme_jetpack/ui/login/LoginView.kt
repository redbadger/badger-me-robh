package com.redbadger.badgerme_jetpack.ui.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.volley.VolleyError
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.navigation.Screen
import com.redbadger.badgerme_jetpack.ui.Title
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme
import com.redbadger.badgerme_jetpack.util.BadgerApiInterface
import com.redbadger.badgerme_jetpack.util.RetrofitHelper
import com.redbadger.badgerme_jetpack.util.addUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun LoginView(navHostController: NavHostController?) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(LocalContext.current.getString(R.string.gsp_id_web))
        .requestEmail()
        .build()

    Box {
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp), verticalArrangement = Arrangement.Top) {
            Title()
        }
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Icons()
        }
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp), verticalArrangement = Arrangement.Bottom) {
            SignInButton(GoogleSignIn.getClient(LocalContext.current, gso), navHostController)
        }
    }
}

@Composable
fun Icons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column{
                Image(
                    painter = painterResource(id = R.drawable.activity_food),
                    contentDescription = "food",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.activity_drinks),
                    contentDescription = "food",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.type_badger),
                    contentDescription = "food",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                )
            }
            Column {
                Image(
                    painter = painterResource(id = R.drawable.activity_walking),
                    contentDescription = "food",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.activity_hugs),
                    contentDescription = "food",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.activity_chats),
                    contentDescription = "food",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                )
            }
        }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 60.dp, end = 60.dp, top = 20.dp),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = "BadgerMe lets you create and join events with other Badgers.",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SignInButton(googleSignInClient: GoogleSignInClient?, navHostController: NavHostController?) {
    val account = remember { mutableStateOf<GoogleSignInAccount?> (null)}
    val signInFailure = remember { mutableStateOf (false) }
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    account.value = task.result
                }
                else {
                    signInFailure.value = true
                }
            }
        }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            shape = RoundedCornerShape(size = 60.dp),
            onClick = {
                startForResult.launch(googleSignInClient?.signInIntent)
            },
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(
                text = "Sign in with Google",
                modifier = Modifier.padding(vertical = 10.dp),
                style = MaterialTheme.typography.button
            )
        }
    }
    account.value?.let {
        handleSignIn(account = it, navHostController = navHostController)
    }
    if (signInFailure.value) {
        Snackbar {
            Text(text = "Google sign in failure")
        }
    }
}

//@Composable
fun handleSignIn(account: GoogleSignInAccount, navHostController: NavHostController?) {
//    val context = LocalContext.current
    val badgerApi = RetrofitHelper.getInstance().create(BadgerApiInterface::class.java)
//    Launch coroutine
    GlobalScope.launch {
        val result = badgerApi.addUser(account.idToken!!, account.email!!)
        if (result.isSuccessful) {
            println(result.toString())
            navHostController?.navigate(Screen.InterestsSetup.route)
        }
        else {
            println(result.errorBody())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BadgerMe_JetpackTheme {
        LoginView(null)
    }
}