package io.github.snpefk.machine

import io.github.snpefk.base.Action
import io.github.snpefk.base.Event
import io.github.snpefk.base.Guard
import io.github.snpefk.base.State

sealed class Transition {
	object NoTransition : Transition()
	object FailedGuard : Transition()

	class Transited<E : Event>(
		val start: State,
		val event: E,
		val target: State,
		val action: Action<E>? = null,
		val guard: Guard<E>? = null
	) : Transition() {

		override fun toString(): String = "Transition(start=$start, event=$event, target=$target)"
	}
}