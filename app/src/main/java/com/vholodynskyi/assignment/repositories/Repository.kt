package com.vholodynskyi.assignment.repositories

import androidx.lifecycle.LiveData
import com.vholodynskyi.assignment.db.contacts.ContactsDao
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.utility.FollowData
import com.vholodynskyi.assignment.di.GlobalFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class Repository(private var contactsDao: ContactsDao) {
    suspend fun getAllContacts()  = flow {
        emit(FollowData.Loading(true))
        val response = GlobalFactory.service.getContacts()
        emit(FollowData.Success(response.results!!))
    }.catch { e ->
        emit(FollowData.Failure(e.message ?: "Unknown Error"))
    }

    fun insert(items:List<DbContact>) {
        CoroutineScope(Dispatchers.IO).launch {
            contactsDao.addAll(items)
        }
    }
    fun getAllFromDatabaseContacts(): LiveData<List<DbContact>> {
        return contactsDao.getContacts()
    }

    fun getContactDeleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            contactsDao.deleteById(id)
        }
    }
    fun getContactById(id: Int): DbContact {
        return contactsDao.getContactById(id)
    }

}