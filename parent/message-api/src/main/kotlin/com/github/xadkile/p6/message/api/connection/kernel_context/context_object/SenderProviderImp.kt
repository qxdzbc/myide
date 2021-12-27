package com.github.xadkile.p6.message.api.connection.kernel_context.context_object

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.unwrap
import com.github.xadkile.p6.exception.error.ErrorReport
import com.github.xadkile.p6.message.api.connection.kernel_context.KernelContextReadOnlyConv
import com.github.xadkile.p6.message.api.connection.service.iopub.IOPubListenerService
import com.github.xadkile.p6.message.api.msg.protocol.JPRawMessage
import com.github.xadkile.p6.message.api.msg.sender.MsgSender
import com.github.xadkile.p6.message.api.msg.sender.composite.CodeExecutionSender
import com.github.xadkile.p6.message.api.msg.sender.composite.ExecuteResult
import com.github.xadkile.p6.message.api.msg.sender.shell.*

class SenderProviderImp internal constructor(
    val kernelContext: KernelContextReadOnlyConv,
) : SenderProvider {

    override fun executeRequestSender2(): MsgSender<ExecuteRequest, Result<ExecuteReply, ErrorReport>> {
        return ExecuteSender(kernelContext)
    }


    override fun kernelInfoSender2(): MsgSender<KernelInfoInput, Result<KernelInfoOutput, ErrorReport>> {
        return KernelInfoSender(kernelContext)
    }


    override fun codeExecutionSender2(): MsgSender<ExecuteRequest, Result<ExecuteResult?, ErrorReport>> {
        return CodeExecutionSender(kernelContext)
    }

}
