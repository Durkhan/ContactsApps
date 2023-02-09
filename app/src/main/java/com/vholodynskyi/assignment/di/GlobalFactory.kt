package com.vholodynskyi.assignment.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.vholodynskyi.assignment.data.api.RetrofitServicesProvider
import com.vholodynskyi.assignment.data.api.contacts.ContactsService
import com.vholodynskyi.assignment.domain.repositories.Repository
import com.vholodynskyi.assignment.data.db.AppDatabase
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.presentation.details.DetailsViewModel
import com.vholodynskyi.assignment.presentation.contactslist.ContactsListViewModel
import com.vholodynskyi.assignment.common.CustomDelegate
import com.vholodynskyi.assignment.data.api.repository.RemoteRepository
import com.vholodynskyi.assignment.domain.usecases.*

object GlobalFactory: ViewModelProvider.Factory {

    val service: ContactsService by lazy {
        RetrofitServicesProvider().contactsService
    }

    lateinit var db: AppDatabase

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }
    var customDelegate: String by CustomDelegate("initial value")
    var deletedItems= arrayListOf<DbContact>()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ContactsListViewModel::class.java -> ContactsListViewModel(
                RemoteRepository(),
                InsertContactsUsecases(Repository(db.userDao())),
                GetAllContactListUsecases(Repository(db.userDao())),
                DeleteContactByIDUsecases(Repository(db.userDao()))
            )
            DetailsViewModel::class.java -> DetailsViewModel(DeleteContactByIDUsecases(Repository(db.userDao())),
                GetContactByIDUsecases(Repository(db.userDao())), UpdateContactUsecases(Repository(db.userDao()))
            )
            else -> throw IllegalArgumentException("Cannot create factory for ${modelClass.simpleName}")
        } as T
    }
}
