package com.lidm.facillify.data.UserPreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lidm.facillify.data.remote.response.UserModelResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore by preferencesDataStore(name="loginSession")

class UserPreferences private constructor(private val dataStore:DataStore<Preferences>) {

    suspend fun saveUserPref(user: UserModelResponse){
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[ROLE_KEY] = user.type
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getUserPref() : Flow<UserModelResponse> {
        return dataStore.data.map { preferences ->
            UserModelResponse(
                msg = "",
                email = preferences[EMAIL_KEY] ?: "",
                token = preferences[TOKEN_KEY] ?: "",
                type = preferences[ROLE_KEY] ?: "",
            )
        }
    }

    suspend fun clearUserPref(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ROLE_KEY = stringPreferencesKey("role")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val dataStore = context.datastore
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}