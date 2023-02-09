package com.vholodynskyi.assignment.domain.usecases

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.repositories.Repository
import kotlinx.coroutines.flow.flow

class UpdateContactUsecases (private var databaseRepository: Repository) {
    operator fun invoke(dbContact: DbContact) = flow {
        try {
            emit(Resource.Loading())
            val result = databaseRepository.update(dbContact)
            emit(Resource.Success(result))
        }catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}