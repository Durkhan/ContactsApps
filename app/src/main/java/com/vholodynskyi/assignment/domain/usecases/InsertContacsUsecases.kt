package com.vholodynskyi.assignment.domain.usecases

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.repositories.Repository
import kotlinx.coroutines.flow.flow

class InsertContactsUsecases(private var databaseRepository: Repository) {
    operator fun invoke(dbContactList:List<DbContact>) = flow {
            try {
                emit(Resource.Loading<List<ApiContact>>())
                val result = databaseRepository.insert(dbContactList)
                emit(Resource.Success(result))
            }catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }
        }

}