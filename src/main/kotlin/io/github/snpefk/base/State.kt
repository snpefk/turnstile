package io.github.snpefk.base

interface State {

	fun onEnter(e: Event) {}

	fun onExit(e: Event) {}
}