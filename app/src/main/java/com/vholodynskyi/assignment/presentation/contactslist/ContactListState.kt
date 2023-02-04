package com.vholodynskyi.assignment.presentation.contactslist

import com.vholodynskyi.assignment.data.db.contacts.DbContact


data class ContactListState(
    val isLoading: Boolean = false,
    var contactList: List<DbContact> = emptyList(),
    val error: String = ""
)