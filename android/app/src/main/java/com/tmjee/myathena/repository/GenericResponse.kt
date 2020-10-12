package com.tmjee.myathena.repository

open class GenericResponse constructor(open var status: String) {
    fun isOk(): Boolean = status === "OK"
}