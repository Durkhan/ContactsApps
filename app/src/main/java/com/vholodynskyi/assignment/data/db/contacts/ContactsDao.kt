package com.vholodynskyi.assignment.data.db.contacts

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM Contact")
    fun getContacts(): List<DbContact>

    @Update
    suspend fun update(contact: DbContact)

    @Insert
    suspend fun addAll(contact: List<DbContact>)

    @Query("SELECT * FROM Contact WHERE id = :contactId")
    fun getContactById(contactId: Int): DbContact

    @Query("DELETE FROM Contact WHERE id = (:contactId)")
    suspend fun deleteById(contactId: Int)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}