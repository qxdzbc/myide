package com.emeraldblast.p6.message.api.message.sender.shell

import com.emeraldblast.p6.common.exception.error.ErrorReport
import com.emeraldblast.p6.message.api.message.sender.MsgSender
import com.github.michaelbull.result.Result

interface KernelInfoSender: MsgSender<KernelInfoInput, Result<KernelInfoOutput, ErrorReport>>
