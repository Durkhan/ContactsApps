package com.vholodynskyi.assignment.presentation.contactslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.common.FollowData
import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.api.contacts.ApiContactResponse
import com.vholodynskyi.assignment.data.api.repository.RemoteRepository
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.usecases.DeleteContactByIDUsecases
import com.vholodynskyi.assignment.domain.usecases.GetAllContactListUsecases
import com.vholodynskyi.assignment.domain.usecases.InsertContactsUsecases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class ContactsListViewModel(var remoteRepository: RemoteRepository,var insertContactsUsecases: InsertContactsUsecases,
                            var getAllContactListUsecases: GetAllContactListUsecases,
                            var deleteContactBYIDUsecases: DeleteContactByIDUsecases
) : ViewModel() {
    var contact = MutableLiveData<DbContact>()
    private val _contacts = MutableStateFlow(ContactListState())
    val contacts : StateFlow<ContactListState> = _contacts.asStateFlow()

    var liveDatalistofContacts= MutableLiveData<FollowData<ApiContactResponse>>()

    init {
        getRemoteContacts()
        getAllContacts()
    }

    private fun getRemoteContacts(){
        viewModelScope.launch {
            remoteRepository.getAllContacts().collect{
                liveDatalistofContacts.postValue(it)
            }
        }
    }


    fun insert(items:List<DbContact>) {
        viewModelScope.launch(Dispatchers.IO) {
            insertContactsUsecases.invoke(items).collect {
                when(it){
                    is Resource.Error ->{
                        Log.d("Error",it.message!!)
                    }
                    else -> {}
                }
            }
        }

    }

    fun getAllContacts(){
        CoroutineScope(Dispatchers.IO).launch {
            getAllContactListUsecases.invoke().collect {
                when (it) {
                    is Resource.Error -> {
                        Log.d("Error", it.message!!)
                    }
                    is Resource.Loading -> ContactListState(isLoading = true)
                    is Resource.Success -> {
                        _contacts.update {state->
                            state.copy(contactList = it.data!! )
                        }
                    }

                }
            }
        }
    }



    fun getContactDeleteById(id:Int){
        viewModelScope.launch {
            deleteContactBYIDUsecases.invoke(id).collect {
                when(it){
                    is Resource.Error->{
                        Log.d("Error", it.message!!)
                    }
                    is Resource.Success->{
                        getAllContacts()
                    }
                    else -> {}
                }
            }
        }

    }



}