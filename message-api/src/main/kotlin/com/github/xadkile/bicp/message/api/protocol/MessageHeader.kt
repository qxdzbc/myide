package com.github.xadkile.bicp.message.api.protocol

import com.github.xadkile.bicp.message.api.protocol.message.MsgType
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class MessageHeader(
    @SerializedName("msg_id")
    val msgId: String,
    @SerializedName("msg_type")
     val msgType: MsgType,
    val username: String,
    @SerializedName("session")
     val sessionId: String,
    val date: LocalDateTime,
    val version: String,
) {
    companion object {
        private val MSG_VERSION = "5.3"

        /**
         * [date] is now
         * [version] is [MSG_VERSION]
         * [msgId] is created as "sessionId_<universalCounter>"
         */
        fun autoCreate(
            msgType: MsgType,
            msgId: String,
            sessionId: String,
            username: String,
        ): MessageHeader {

            return MessageHeader(
                msgId = msgId,
                msgType = msgType,
                username = username,
                sessionId = sessionId,
                date = LocalDateTime.now(),
                version = MSG_VERSION
            )
        }
    }

}
