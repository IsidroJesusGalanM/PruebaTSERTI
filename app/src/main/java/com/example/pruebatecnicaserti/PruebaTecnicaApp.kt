package com.example.pruebatecnicaserti

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class PruebaTecnicaApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("reqres-db")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }

}