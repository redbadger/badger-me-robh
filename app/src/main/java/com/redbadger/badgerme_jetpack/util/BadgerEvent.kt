package com.redbadger.badgerme_jetpack.util

data class BadgerEvent (
    var name: String,
    var owner: BadgerUser,
    var attendees: List<BadgerUser>,
    var tags: List<BadgerInterest>,
    var startTime: String,
    var endTime: String
)