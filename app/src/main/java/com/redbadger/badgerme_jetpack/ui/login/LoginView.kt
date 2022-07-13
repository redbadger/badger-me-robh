package com.redbadger.badgerme_jetpack.ui.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
import kotlinx.coroutines.*


@Composable
fun LoginView(navHostController: NavHostController?, viewModel: LoginViewModel = viewModel()) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(LocalContext.current.getString(R.string.gsp_id_web))
        .requestEmail()
        .build()

    if (viewModel.loading.value) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp), verticalArrangement = Arrangement.Top
            ) {
                Title()
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Icons()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp), verticalArrangement = Arrangement.Bottom
            ) {
                SignInButton(
                    GoogleSignIn.getClient(LocalContext.current, gso),
                    navHostController,
                    viewModel
                )
            }
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
fun SignInButton(
    googleSignInClient: GoogleSignInClient?,
    navHostController: NavHostController?,
    viewModel: LoginViewModel
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val startForResult =
    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    signIn(
                        account = task.result,
                        navHostController = navHostController,
                        snackbarHostState,
                        scope,
                        false,
                        viewModel
                    )
                }
                else {
                    scope.launch{
                        onError(snackbarHostState,"Google Sign in failure")
                    }
                }
            }
        }

    SnackbarHost(hostState = snackbarHostState)
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
}

fun register (
    account: GoogleSignInAccount,
    navHostController: NavHostController?,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    viewModel: LoginViewModel
) {
    val badgerApi = RetrofitHelper.getInstance().create(BadgerApiInterface::class.java)
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarScope.launch {
            onError(snackbarHostState, "Exception: ${throwable.localizedMessage}")
        }
    }

    CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = badgerApi.addUser("Bearer ${account.idToken!!}", account.email!!)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful)
            {
                println(response.toString())
                snackbarScope.launch {
                    onError(snackbarHostState, response.message().ifEmpty { "Registration success! Proceeding to sign in..." })
                }
                signIn(
                    account,
                    navHostController,
                    snackbarHostState,
                    snackbarScope,
                    true,
                    viewModel
                )
            } else
            {
                println(response.errorBody().toString())
                snackbarScope.launch {
                    onError(snackbarHostState, response.message().ifEmpty { "Error! Response code ${response.code()}" })
                }
            }
        }
    }
}

fun signIn(account: GoogleSignInAccount,
           navHostController: NavHostController?,
           snackbarHostState: SnackbarHostState,
           snackbarScope: CoroutineScope,
           newUser: Boolean,
           viewModel: LoginViewModel) {
    val badgerApi = RetrofitHelper.getInstance().create(BadgerApiInterface::class.java)
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarScope.launch {
            onError(snackbarHostState, "Exception: ${throwable.localizedMessage}")
        }
    }
    viewModel.loading.value = true

    CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = badgerApi.getUserByEmail("Bearer ${account.idToken!!}", account.email!!)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful)
            {
                if (response.body()?.isEmpty() == true) {
                    snackbarScope.launch {
                        onError(snackbarHostState, "DEBUG: No matching Badgers found - proceeding to register...")
                    }
                    register(account, navHostController, snackbarHostState, snackbarScope, viewModel)
                }
                else {
//                    snackbarScope.launch {
//                        onError(snackbarHostState, "DEBUG: Got ${response.body()?.get(0)?.firstName} ${response.body()?.get(0)?.lastName}!")
//                    }
                    if (newUser) {
                        navHostController?.navigate(
                            "${Screen.InterestsSetup.route}/" +
                                    "${response.body()?.get(0)?.id}/" +
                                    "Bearer ${account.idToken!!}"
                        )
                    }
                    else {
                        navHostController?.navigate(
                            "${Screen.Home.route}/${response.body()?.get(0)?.id}/Bearer ${account.idToken!!}"
                        )
                    }
                }
            }
            else {
                viewModel.loading.value = false
                snackbarScope.launch {
                    onError(snackbarHostState, response.message().ifEmpty { "Error! Response code ${response.code()}" })
                }
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
        LoginView(null)
    }
}