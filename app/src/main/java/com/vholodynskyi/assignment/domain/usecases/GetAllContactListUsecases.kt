package com.vholodynskyi.assignment.domain.usecases


import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetAllContactListUsecases(private var databaseRepository: Repository) {
    operator fun invoke():  Flow<Resource<List<DbContact>>> = flow {
        try {
            emit(Resource.Loading<List<DbContact>>())
            val contacts = databaseRepository.getAllFromDatabaseContacts()
            emit(Resource.Success<List<DbContact>>(contacts))
        } catch (e: HttpException) {
            emit(Resource.Error<List<DbContact>>(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error<List<DbContact>>(e.localizedMessage))
        }
    }
}
