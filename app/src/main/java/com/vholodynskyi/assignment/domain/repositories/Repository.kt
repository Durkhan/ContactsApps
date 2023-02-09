package com.vholodynskyi.assignment.domain.repositories

import com.vholodynskyi.assignment.data.db.contacts.ContactsDao
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Repository(private var contactsDao: ContactsDao) {
    fun insert(items:List<DbContact>) {
        CoroutineScope(Dispatchers.IO).launch {
            contactsDao.addAll(items)
        }
    }
    fun getAllFromDatabaseContacts(): List<DbContact> {
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
    fun update(dbContact: DbContact) {
        CoroutineScope(Dispatchers.IO).launch {
            contactsDao.update(dbContact)
        }
    }
}