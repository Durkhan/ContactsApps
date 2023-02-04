package com.vholodynskyi.assignment.common

import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.data.api.contacts.ApiContactResponse

sealed class FollowData<ApiContactResponse> {
    data class Loading(val isLoading: Boolean) : FollowData<ApiContactResponse>()
    data class Success(val isSuccess: List<ApiContact>) : FollowData<ApiContactResponse>()
    data class Failure(val errorMessage: String) : FollowData<ApiContactResponse>()
}