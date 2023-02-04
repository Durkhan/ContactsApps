package com.vholodynskyi.assignment.ui.details
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(var repository:Repository) : ViewModel() {
    var contact = MutableLiveData<DbContact>()

    fun getContactById(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                contact.postValue(repository.getContactById(id))
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    fun getContactDeleteById(id:Int){
        return repository.getContactDeleteById(id)
    }

}