package io.github.snpefk.base

fun interface Guard<T : Event> {

	operator fun invoke(event: T): Boolean
}