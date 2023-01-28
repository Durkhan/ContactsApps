package com.vholodynskyi.assignment.api

import com.vholodynskyi.assignment.api.contacts.ContactsService
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitServicesProvider {
    private val moshi: Moshi = Moshi.Builder()
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val contactsService: ContactsService
        get() = retrofit.create(ContactsService::class.java)
}