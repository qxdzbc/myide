package org.bitbucket.xadkile.myide.ide.jupyter.message.api.session

import java.util.*

/**
 * A connection session.
 * A session is unique and one-time use.
 * When the kernel restart, the old session must be closed and a new session must be created.
 * A closed session cannot be reused
 */
class Session(
    sessionId: String,
    username: String,
    key: String
) {
    val sessionId: String = sessionId
        get() {
            return getPropWithCheck(field, "sessionId = [${field}]")
        }

    val username: String = username
        get() {
            return getPropWithCheck(field, "username = [${field}]")
        }
    val key: String = key
        get() {
            return getPropWithCheck(field, "key = [${field}]")
        }

    private var _isOpen: Boolean = true
    fun isOpen(): Boolean {
        return _isOpen
    }

    fun close() {
        this._isOpen = false
    }

    private fun <T> getPropWithCheck(propValue: T, propName: String): T {
        if (this.isOpen()) {
            return propValue
        } else {
            throw IllegalStateException(ExceptionMessage(propName))
        }
    }

    companion object {
        private fun ExceptionMessage(propName: String) = "Session is closed, can't get property $propName"

        /**
         * [sessionId] autogenerated
         * [username] fetched from system
         */
        fun autoCreate(key: String): Session {
            return Session(
                sessionId = UUID.randomUUID().toString(),
                username = System.getProperty("user.name"),
                key = key
            )
        }
    }


}
