package com.example.pruebatecnicaserti.data.realm.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MyEntityList (
    @PrimaryKey
    private var id: Int = 1,
    var items: RealmList<String> = RealmList()
    ): RealmObject()