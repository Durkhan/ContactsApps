package com.vholodynskyi.assignment.repositories

import com.vholodynskyi.assignment.utility.FollowData
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