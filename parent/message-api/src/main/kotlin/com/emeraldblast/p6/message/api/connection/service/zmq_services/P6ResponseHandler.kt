package com.emeraldblast.p6.message.api.connection.service.zmq_services

import com.emeraldblast.p6.message.api.connection.service.zmq_services.msg.P6Message
import com.emeraldblast.p6.message.api.connection.service.zmq_services.msg.P6Response

interface P6ResponseHandler :P6Handler<P6Response> {
//    val id:String
//    fun handleMessage(msg:P6Response)
}
