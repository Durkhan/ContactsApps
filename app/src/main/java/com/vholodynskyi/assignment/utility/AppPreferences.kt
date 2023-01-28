package com.vholodynskyi.assignment.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

class AppPreferences(private val dataStore: DataStore<Preferences>) {



    val isEmpty:Flow<Boolean>
      get() = dataStore.data.map {
          it[is_Empty]?:true
      }

    private companion object {
        val is_Empty= booleanPreferencesKey(name="is_Empty")
    }

    suspend fun setIsNotEmpty(boolean: Boolean){
        dataStore.edit {
            it[is_Empty]=boolean
        }
    }
}