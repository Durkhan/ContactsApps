package com.vholodynskyi.assignment.ui.contactslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.utility.FollowData
import com.vholodynskyi.assignment.repositories.Repository
import com.vholodynskyi.assignment.api.contacts.ApiContactResponse
import com.vholodynskyi.assignment.db.contacts.DbContact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ContactsListViewModel(var repository: Repository): ViewModel() {
    var liveDatalistofContacts= MutableLiveData<FollowData<ApiContactResponse>>()
    private var _contacts=MutableLiveData<List<DbContact>>()
    var contacts:LiveData<List<DbContact>> =_contacts

    init {
        getContacts()
        getAllFromDatabaseContacts()
    }
     fun getAllFromDatabaseContacts(): LiveData<List<DbContact>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                contacts=repository.getAllFromDatabaseContacts()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return contacts
    }
    fun insert(items:List<DbContact>) {
        viewModelScope.launch {
            repository.insert(items)
        }

    }
    private fun getContacts(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllContacts().collect{
                liveDatalistofContacts.postValue(it)
            }
        }
    }
    fun getContactDeleteById(id:Int){
        return repository.getContactDeleteById(id)
    }

}

