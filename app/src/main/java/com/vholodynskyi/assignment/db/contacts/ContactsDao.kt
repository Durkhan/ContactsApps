package com.vholodynskyi.assignment.db.contacts

import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM Contact")
    suspend fun getContacts(): List<DbContact>

    @Update
    suspend fun update(contact: DbContact)

    @Insert
    suspend fun addAll(contact: List<DbContact>)

    @Query("DELETE FROM Contact WHERE id = (:contactId)")
    suspend fun deleteById(contactId: Int)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}