package com.github.xadkile.bicp.message.api.connection.kernel_context

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.xadkile.bicp.message.api.channel.ChannelInfo
import com.github.xadkile.bicp.message.api.connection.kernel_context.context_object.*
import com.github.xadkile.bicp.message.api.connection.service.heart_beat.HeartBeatService
import com.github.xadkile.bicp.message.api.connection.service.heart_beat.HeartBeatServiceConv
import com.github.xadkile.bicp.message.api.msg.protocol.other.MsgIdGenerator
import org.zeromq.ZContext

interface KernelContextReadOnlyConv : KernelContextReadOnly {

    fun getHeartBeatChannel(): Result<ChannelInfo, Exception> {
        return this.getChannelProvider().map { it.heartbeatChannel() }
    }

    fun getHeartBeatAddress(): Result<String, Exception> {
        return this.getHeartBeatChannel().map { it.makeAddress() }
    }

    fun getShellChannel():Result<ChannelInfo,Exception>{
        return this.getChannelProvider().map { it.shellChannel() }
    }

    fun getShellAddress():Result<String,Exception>{
        return this.getChannelProvider().map { it.shellAddress() }
    }

    fun getConvHeartBeatService(): Result<HeartBeatServiceConv, Exception> {
        return this.getHeartBeatService().map { it.conv() }
    }

    fun getIOPubChannel():Result<ChannelInfo,Exception>{
        return this.getChannelProvider().map { it.ioPubChannel() }
    }

    fun getIOPubAddress():Result<String,Exception>{
        return this.getChannelProvider().map { it.ioPUBAddress() }
    }

    fun getControlChannel():Result<ChannelInfo,Exception>{
        return this.getChannelProvider().map { it.controlChannel() }
    }

    fun getControlAddress():Result<String,Exception>{
        return this.getChannelProvider().map { it.controlAddress() }
    }


    fun original(): KernelContextReadOnly

    override fun getSocketProvider(): Result<SocketProvider, Exception> {
        return this.original().getSocketProvider()
    }

    override fun getConnectionFileContent(): Result<com.github.xadkile.bicp.message.api.msg.protocol.KernelConnectionFileContent, Exception> {
        return this.original().getConnectionFileContent()
    }

    override fun getSession(): Result<Session, Exception> {
        return this.original().getSession()
    }

    override fun getChannelProvider(): Result<ChannelProvider, Exception> {
        return this.original().getChannelProvider()
    }

    override fun getSenderProvider(): Result<SenderProvider, Exception> {
        return this.original().getSenderProvider()
    }

    override fun getMsgEncoder(): Result<MsgEncoder, Exception> {
        return this.original().getMsgEncoder()
    }

    override fun getMsgIdGenerator(): Result<MsgIdGenerator, Exception> {
        return this.original().getMsgIdGenerator()
    }

    override fun getHeartBeatService(): Result<HeartBeatService, Exception> {
        return this.original().getHeartBeatService()
    }

    override fun zContext(): ZContext {
        return this.original().zContext()
    }

    override fun isRunning(): Boolean {
        return this.original().isRunning()
    }

    override fun isNotRunning(): Boolean {
        return this.original().isNotRunning()
    }
}

