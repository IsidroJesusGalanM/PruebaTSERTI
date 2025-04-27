package com.example.pruebatecnicaserti.di

import android.content.Context
import com.example.pruebatecnicaserti.network.ReqresApi
import com.example.pruebatecnicaserti.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): ReqresApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReqresApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRealmConfiguration(@ApplicationContext context: Context): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name("reqres-db.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    @Provides
    @Singleton
    fun provideRealm(config: RealmConfiguration): Realm {
        return Realm.getInstance(config)
    }
}