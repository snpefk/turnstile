package io.github.snpefk.base

fun interface Action<T : Event> {

	operator fun invoke(event: T)
}