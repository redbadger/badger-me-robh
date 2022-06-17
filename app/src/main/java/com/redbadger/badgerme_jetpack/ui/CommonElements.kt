package com.redbadger.badgerme_jetpack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redbadger.badgerme_jetpack.R
import com.redbadger.badgerme_jetpack.ui.theme.BadgerMe_JetpackTheme

@Composable
fun Title() {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Box () {
            Column (modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.orange_box),
                    contentDescription = "Title flair",
                    modifier = Modifier
                        .padding(start = 80.dp)
                        .size(40.dp)
                )
            }
            Column (modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Badger Me",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BadgerMe_JetpackTheme {
        Title()
    }
}