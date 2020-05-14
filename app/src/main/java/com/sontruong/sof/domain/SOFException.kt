package com.sontruong.sof.domain

import java.lang.Exception

open class SOFException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(throwable: Throwable) : super(throwable)
}

class UnknownException : SOFException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(throwable: Throwable) : super(throwable)
}