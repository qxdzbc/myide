package com.github.xadkile.p6.message.api.msg.sender.shell

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.unwrap
import com.github.xadkile.p6.exception.error.ErrorReport
import com.github.xadkile.p6.message.api.connection.kernel_context.KernelContextReadOnlyConv
import com.github.xadkile.p6.message.api.connection.kernel_context.context_object.MsgEncoder
import com.github.xadkile.p6.message.api.connection.kernel_context.exception.KernelErrors
import com.github.xadkile.p6.message.api.connection.kernel_context.exception.KernelIsDownException
import com.github.xadkile.p6.message.api.connection.service.heart_beat.HeartBeatService
import com.github.xadkile.p6.message.api.msg.protocol.JPMessage
import com.github.xadkile.p6.message.api.msg.protocol.data_interface_definition.Shell
import com.github.xadkile.p6.message.api.msg.sender.MsgSender
import com.github.xadkile.p6.message.api.msg.sender.PCSender
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.zeromq.ZContext
import org.zeromq.ZMQ


typealias KernelInfoInput = JPMessage<Shell.KernelInfo.Request.MetaData, Shell.KernelInfo.Request.Content>
typealias KernelInfoOutput = JPMessage<Shell.KernelInfo.Reply.MetaData, Shell.KernelInfo.Reply.Content>

class KernelInfoSender internal constructor(
    val kernelContext: KernelContextReadOnlyConv,
) : MsgSender<KernelInfoInput, Result<KernelInfoOutput, ErrorReport>> {

    override suspend fun send(
        message: KernelInfoInput,
        dispatcher: CoroutineDispatcher,
    ): Result<KernelInfoOutput, ErrorReport> {
        if (kernelContext.isKernelNotRunning()) {
            return Err(ErrorReport(
                header = KernelErrors.KernelDown,
                data = KernelErrors.KernelDown.Data(""),
                loc = "${this.javaClass.canonicalName}.send"
            ))
        }
        return withContext(dispatcher) {
            val socket: ZMQ.Socket = kernelContext.getSocketProvider2().unwrap().shellSocket()
            val msgEncoder: MsgEncoder = kernelContext.getMsgEncoder2().unwrap()
            val hbService: HeartBeatService = kernelContext.getHeartBeatService2().unwrap()
            val zContext: ZContext = kernelContext.zContext()
            val zSender = PCSender<KernelInfoInput, KernelInfoOutput>(socket, msgEncoder, hbService, zContext)
            val rt = zSender.send2<Shell.KernelInfo.Request.MetaData, Shell.KernelInfo.Request.Content>(message)
            rt
        }
    }
}
