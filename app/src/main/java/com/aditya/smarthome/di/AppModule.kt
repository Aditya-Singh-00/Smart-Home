package com.aditya.smarthome.di

import com.aditya.smarthome.data.repository.SmartHomeRepository
import com.aditya.smarthome.data.repository.SmartHomeRepositoryImpl
import com.aditya.smarthome.data.source.AuthorizationApi
import com.aditya.smarthome.data.source.DeviceApi
import com.aditya.smarthome.util.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance(DATABASE_URL)
    }

    @Provides
    @Singleton
    fun provideAuthorizationApi(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase
    ): AuthorizationApi {
        return AuthorizationApi(firebaseAuth,firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideDevices(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase
    ): DeviceApi {
        return DeviceApi(firebaseAuth, firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideSmartHomeRepository(
        authorization: AuthorizationApi,
        device: DeviceApi
    ): SmartHomeRepository {
        return SmartHomeRepositoryImpl(authorization, device)
    }
}