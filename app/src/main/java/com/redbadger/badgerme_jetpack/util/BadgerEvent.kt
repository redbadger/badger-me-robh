package com.redbadger.badgerme_jetpack.util

data class BadgerEvent (
    var name: String,
    var created_by: BadgerUser,
    var attendees: List<BadgerUser> = listOf(),
    var tags: List<BadgerInterest> = listOf(),
    var startTime: String,
    var endTime: String,
    var location: String
)