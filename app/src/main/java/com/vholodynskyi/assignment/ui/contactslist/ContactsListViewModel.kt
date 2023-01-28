package com.vholodynskyi.assignment.ui.contactslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.api.utility.FollowData
import com.vholodynskyi.assignment.api.repositories.Repository
import com.vholodynskyi.assignment.api.contacts.ApiContactResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ContactsListViewModel(var repository: Repository): ViewModel() {
    var liveDatalistofContacts= MutableLiveData<FollowData<ApiContactResponse>>()

    init {
        getContacts()
    }

    private fun getContacts(){
        viewModelScope.launch {
            repository.getAllContacts().collect{
                liveDatalistofContacts.postValue(it)
            }
        }
    }
}

