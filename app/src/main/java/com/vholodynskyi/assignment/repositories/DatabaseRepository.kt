package com.vholodynskyi.assignment.repositories

import androidx.lifecycle.LiveData
import com.vholodynskyi.assignment.db.contacts.ContactsDao
import com.vholodynskyi.assignment.db.contacts.DbContact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseRepository(private var contactsDao: ContactsDao){

    fun insert(items:List<DbContact>) {
        CoroutineScope(Dispatchers.IO).launch {
            contactsDao.addAll(items)
        }
    }

    fun getAllContacts(): LiveData<List<DbContact>> {
        return contactsDao.getContacts()
    }

    fun getContactById(id: Int): DbContact {
        return contactsDao.getContactById(id)
    }





}