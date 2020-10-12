package com.tmjee.myathena.ui

class Evt<T> constructor(val type: String, _payload: T? = null) {
    var handled: Boolean = false

    var payload: T? = _payload
        private set
        get() {
            handled = true
            return field
        }

    var peekPayload: T? = _payload
        private set
}





