package com.example.pruebatecnicaserti.data.realm.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class VerifiedUserEntity: RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var email: String = ""
    var token: String = ""
}
