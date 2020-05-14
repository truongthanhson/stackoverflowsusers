package com.sontruong.sof.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import java.lang.Exception

/**
 * Executes business logic synchronously or asynchronously using a [Scheduler].
 */
abstract class UseCase<in P, R> {
    protected var taskScheduler: Scheduler = DefaultScheduler

    /** Executes the use case asynchronously and places the [Result] in a MutableLiveData
     *
     * @param parameters the input parameters to run the use case with
     * @param result the MutableLiveData where the result is posted to
     *
     */
    operator fun invoke(parameters: P, result: MutableLiveData<Result<R>>) {
        try {
            taskScheduler.execute {
                try {
                    execute(parameters).let { useCaseResult ->
                        result.postValue(Result.Success(useCaseResult))
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    result.postValue(Result.Error(e))
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            result.postValue(Result.Error(e))
        }
    }

    /** Executes the use case asynchronously and returns a [Result] in a new LiveData object.
     *
     * @return an observable [LiveData] with a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    operator fun invoke(parameters: P): LiveData<Result<R>> {
        val liveDataCallback: MutableLiveData<Result<R>> = MutableLiveData()
        this(parameters, liveDataCallback)
        return liveDataCallback
    }

    /** Executes the use case synchronously  */
    fun executeNow(parameters: P): Result<R> {
        return try {
            Result.Success(execute(parameters))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}

operator fun <R> UseCase<Unit, R>.invoke(): LiveData<Result<R>> = this(Unit)
operator fun <R> UseCase<Unit, R>.invoke(result: MutableLiveData<Result<R>>) = this(Unit, result)