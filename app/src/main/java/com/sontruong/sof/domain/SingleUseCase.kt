package com.sontruong.sof.domain

import io.reactivex.Single

abstract class SingleUseCase<in P, R> {

    protected abstract fun execute(param: P): Single<Result<R>>

    operator fun invoke(parameters: P): Single<Result<R>> = execute(parameters)

    protected fun success(data: R): Result<R> {
        return Result.Success(data)
    }

    protected fun failed(exception: Exception): Result<Nothing> {
        return Result.Error(exception)
    }
}

operator fun <R> SingleUseCase<Unit, R>.invoke(): Single<Result<R>> = this(Unit)