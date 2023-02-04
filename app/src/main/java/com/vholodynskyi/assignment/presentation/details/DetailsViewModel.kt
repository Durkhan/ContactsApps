package com.vholodynskyi.assignment.presentation.details
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.usecases.DeleteContactByIDUsecases
import com.vholodynskyi.assignment.domain.usecases.GetContactByIDUsecases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(var deleteContactBYIDUsecases: DeleteContactByIDUsecases,
                       var getContactByIDUsecases: GetContactByIDUsecases
) : ViewModel() {
    var contact = MutableLiveData<DbContact>()

    fun getContactById(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            getContactByIDUsecases.invoke(id).collect {
                when (it) {
                    is Resource.Error -> {
                        Log.d("Error", it.message!!)
                    }
                    is Resource.Success->{
                        contact.postValue(it.data!!)
                    }
                    else -> {}
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
                    else -> {}
                }
            }
        }

    }


}