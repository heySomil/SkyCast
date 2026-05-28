package com.avis.skycast.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "user_session"
)

class SessionManager @Inject constructor(

    @ApplicationContext
    private val context: Context
) {

    companion object {

        private val IS_LOGGED_IN =
            booleanPreferencesKey(
                "is_logged_in"
            )
    }

    suspend fun saveLoginState(
        isLoggedIn: Boolean
    ) {

        context.dataStore.edit {

            it[IS_LOGGED_IN] =
                isLoggedIn
        }
    }

    val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map {

            it[IS_LOGGED_IN] ?: false
        }
}