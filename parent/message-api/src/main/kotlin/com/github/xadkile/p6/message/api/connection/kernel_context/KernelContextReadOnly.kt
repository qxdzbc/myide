package com.github.xadkile.p6.message.api.connection.kernel_context

import com.github.michaelbull.result.Result
import com.github.xadkile.p6.exception.error.ErrorReport
import com.github.xadkile.p6.message.api.connection.kernel_context.context_object.*
import com.github.xadkile.p6.message.api.connection.service.heart_beat.HeartBeatService
import com.github.xadkile.p6.message.api.connection.service.iopub.IOPubListenerService
import com.github.xadkile.p6.message.api.connection.service.iopub.IOPubListenerServiceReadOnly
import com.github.xadkile.p6.message.api.msg.protocol.KernelConnectionFileContent
import com.github.xadkile.p6.message.api.msg.protocol.other.MsgIdGenerator
import org.zeromq.ZContext

/**
 * Limiting interface to safely access context-bound objects.
 *
 * This is for preventing mistakenly changing IPython context state, such as calling start, stop in a sender.
 */
interface KernelContextReadOnly {

    fun getKernelConfig(): KernelConfig

    fun getIOPubListenerService():Result<IOPubListenerServiceReadOnly,Exception>
    fun getIOPubListenerService2():Result<IOPubListenerServiceReadOnly,ErrorReport>

    /**
     * Return content of connection file .
     *
     * Connection file is available for use only when IPython process is launch successfully.
     */
    fun getConnectionFileContent(): Result<KernelConnectionFileContent, Exception>
    fun getConnectionFileContent2(): Result<KernelConnectionFileContent, ErrorReport>

    fun getSession(): Result<Session, Exception>
    fun getSession2(): Result<Session, ErrorReport>

    fun getChannelProvider(): Result<ChannelProvider, Exception>
    fun getChannelProvider2(): Result<ChannelProvider, ErrorReport>

    fun getSenderProvider(): Result<SenderProvider, Exception>
    fun getSenderProvider2(): Result<SenderProvider, ErrorReport>

    fun getMsgEncoder(): Result<MsgEncoder, Exception>
    fun getMsgEncoder2(): Result<MsgEncoder, ErrorReport>

    fun getMsgIdGenerator(): Result<MsgIdGenerator, Exception>
    fun getMsgIdGenerator2(): Result<MsgIdGenerator, ErrorReport>

    fun getHeartBeatService():Result<HeartBeatService,Exception>
    fun getHeartBeatService2():Result<HeartBeatService,ErrorReport>

    fun getSocketProvider():Result<SocketProvider,Exception>
    fun getSocketProvider2():Result<SocketProvider,ErrorReport>

    fun zContext(): ZContext
    /**
     * convert this to a more convenient but more dangerous to use interface
     */
    fun conv():KernelContextReadOnlyConv

    /**
     * kernel process and all context-related objects are on and safe to get
     */
    fun isKernelRunning():Boolean

    /**
     * all context-related services are running
     */
    fun areServicesRunning():Boolean
    /**
     * A running context guarantees that all context-related objects and services are up, running, not null
     */
    fun isAllRunning():Boolean

    /**
     * A stopped context guarantees that all context-related objects and services are stopped and null
     */
    fun isKernelNotRunning():Boolean

}

