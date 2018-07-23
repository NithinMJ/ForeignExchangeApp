package com.nithin.foreignexchangeapp

import org.mockito.Mockito

fun <T> anyNotNull(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T