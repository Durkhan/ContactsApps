package com.vholodynskyi.assignment.db



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.repositories.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(var databaseRepository: DatabaseRepository) : ViewModel() {
    var contact = MutableLiveData<DbContact>()
    fun insert(items:List<DbContact>) {
        viewModelScope.launch {
            databaseRepository.insert(items)
        }

    }

    fun getAllContacts(): LiveData<List<DbContact>> {
        return databaseRepository.getAllContacts()
    }

    fun getContactById(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                contact.postValue(databaseRepository.getContactById(id))
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    fun getContactDeleteById(id:Int){
        return databaseRepository.getContactDeleteById(id)
    }




}