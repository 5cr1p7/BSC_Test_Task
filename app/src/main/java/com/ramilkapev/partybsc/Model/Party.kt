package com.ramilkapev.partybsc.Model

import kotlinx.serialization.Serializable

@Serializable
data class Party (val partyName : String, val partyPicture : String, val users : List<Users>)

@Serializable
data class Users (val id : Int, val name : String, val picture : String)