package com.vholodynskyi.assignment.api.repositories

import com.vholodynskyi.assignment.api.utility.FollowData
import com.vholodynskyi.assignment.di.GlobalFactory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class Repository {
    suspend fun getAllContacts()  = flow {
        emit(FollowData.Loading(true))
        val response = GlobalFactory.service.getContacts()
        emit(FollowData.Success(response.results!!))
    }.catch { e ->
        emit(FollowData.Failure(e.message ?: "Unknown Error"))
    }


}