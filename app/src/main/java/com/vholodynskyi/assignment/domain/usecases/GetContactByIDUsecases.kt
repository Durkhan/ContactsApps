package com.vholodynskyi.assignment.domain.usecases

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetContactByIDUsecases (private var databaseRepository: Repository) {
    operator fun invoke(id:Int): Flow<Resource<DbContact>> = flow {
        try {
            emit(Resource.Loading<DbContact>())
            val result = databaseRepository.getContactById(id)
            emit(Resource.Success(result))
        }catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }


}