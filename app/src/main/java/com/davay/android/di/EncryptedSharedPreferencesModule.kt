package com.davay.android.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.davay.android.core.di.storage.marker.StorageMarker
import com.davay.android.core.di.storage.model.PreferencesStorage
import dagger.Module
import dagger.Provides

@Module
class EncryptedSharedPreferencesModule {
    @StorageMarker(PreferencesStorage.USER)
    @Provides
    fun encryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "UserInfo",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return encryptedSharedPreferences
    }
}
