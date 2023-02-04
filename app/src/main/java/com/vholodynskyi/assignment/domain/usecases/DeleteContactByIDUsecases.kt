package com.vholodynskyi.assignment.domain.usecases

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.repositories.Repository
import kotlinx.coroutines.flow.flow


class DeleteContactByIDUsecases(private var databaseRepository: Repository) {
    operator fun invoke(id:Int) = flow {
        try {
            emit(Resource.Loading<List<DbContact>>())
            val result = databaseRepository.getContactDeleteById(id)
            emit(Resource.Success(result))
        }catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }


}