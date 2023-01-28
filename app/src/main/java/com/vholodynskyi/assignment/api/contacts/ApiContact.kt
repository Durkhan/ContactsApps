package com.vholodynskyi.assignment.api.contacts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiContact(
    val name: Name?,
    val email: String?,
    val picture: Picture?
)

@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "first")
    val firstName: String?,
    @Json(name = "last")
    val lastName: String?,
    val picture: Picture?
)

@JsonClass(generateAdapter = true)
data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?
)