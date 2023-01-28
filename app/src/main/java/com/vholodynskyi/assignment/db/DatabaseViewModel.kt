package com.vholodynskyi.assignment.db



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.repositories.DatabaseRepository
import kotlinx.coroutines.launch

class DatabaseViewModel(var databaseRepository: DatabaseRepository) : ViewModel() {

    fun insert(items:List<DbContact>) {
        viewModelScope.launch {
            databaseRepository.insert(items)
        }

    }

    fun getAllContacts(): LiveData<List<DbContact>> {
        return databaseRepository.getAllContacts()
    }






}