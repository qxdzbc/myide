package com.github.xadkile.bicp.message.api.connection.process_watcher

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.xadkile.bicp.message.api.exception.UnknownException
import kotlinx.coroutines.*

/**
 * Watch a process from a separated thread and react when the process stops.
 * This watcher can actually catch the kill signal from outside this app.
 * the listener only triggers on exit event of the process.
 * If the watcher is stopped midway, the listener must NOT trigger, and the process must not be affected
 */
class OnStopWatcher(
    private var onStopListener: OnStopEventProcessListener = OnStopEventProcessListener.nothing,
    private var onErrListener: OnErrEventProcessListener = OnErrEventProcessListener.nothing,
    private val cScope: CoroutineScope,
    private val cDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ProcessWatcher {

    private var job: Job? = null
    override fun startWatching(process: Process): Result<Unit, Exception> {
        if (this.isWatching().not() && process.isAlive) {
            this.job = cScope.launch(cDispatcher) {
                try {
                    while (isActive) {
                        if (!process.isAlive) {
                            onStopListener.onStop(process)
                            break
                        }
                    }
                } catch (e: InterruptedException) {
                    onErrListener.onError(process, e)
                }
            }
            return Ok(Unit)
        } else {
            if (this.isWatching()) return Err(ProcessWatcherIllegalStateException("Process watcher is already running"))
            if (!process.isAlive) return Err(ProcessWatcherIllegalStateException("Cannot watch dead process"))
        }
        return Err(UnknownException("OnStopWatcher: impossible"))

    }

    override fun stopWatching(): Result<Unit, Exception> {
        if (this.isWatching()) {
            runBlocking {
                job?.cancel()
                job?.join()
            }
        }
        return Ok(Unit)
    }

    override fun isWatching(): Boolean {
        return this.job?.isActive == true
    }
}
