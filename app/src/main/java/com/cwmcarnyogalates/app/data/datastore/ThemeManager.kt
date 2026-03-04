package com.cwmcarnyogalates.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themeDataStore by preferencesDataStore(name = "theme_prefs")

class ThemeManager(private val context: Context) {

    fun observeDarkTheme(): Flow<Boolean> =
        context.themeDataStore.data.map { it[DARK_THEME_KEY] ?: false }

    suspend fun setDarkTheme(enabled: Boolean) {
        context.themeDataStore.edit { it[DARK_THEME_KEY] = enabled }
    }

    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("DARK_THEME")
    }
}
