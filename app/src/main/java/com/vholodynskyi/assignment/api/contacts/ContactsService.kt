package com.vholodynskyi.assignment.api.contacts

import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsService {
    @GET("api/")
    suspend fun getContacts(@Query("results") limit: Int = 30): ApiContactResponse
}