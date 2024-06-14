package com.myandroid.blinkitclone.models

data class Users(
    var uid: String? = null,
    var userPhoneNumber: String? = null,
    var userAddress: String? = null,
    var userToken: String? = null,
)
{
    // No-args constructor required for Firebase
    constructor() : this(null, null, null, null)
}