package com.redbadger.badgerme_jetpack.util

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

fun addUser(email: String, token: String, context: Context) {
    val url = "https://leafy-stroopwafel-d2f43b.netlify.app/api/"
    // Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(context)
    val params = JSONObject()
    params.put("email", email)

    val jsonObjectRequest = object: JsonObjectRequest(
        Method.GET,
        url,
        params,
        Response.Listener { response ->
            println("Response: %s".format(response.toString()))
        },
        Response.ErrorListener { err ->
//            Need to work out best way of pushing this up to the page for a snackbar
//            Also need to wait for a response before continuing
            println("Error: %s".format(err.message))
        }
    ){
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Authorization"] = token
            return headers
        }
    }

    // Add the request to the RequestQueue.
    queue.add(jsonObjectRequest)
}
