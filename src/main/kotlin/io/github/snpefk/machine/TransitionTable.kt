package io.github.snpefk.machine

import io.github.snpefk.base.Action
import io.github.snpefk.base.Event
import io.github.snpefk.base.Guard
import io.github.snpefk.base.State
import kotlin.reflect.KClass

class TransitionTable(private val transitions: List<Row<State, Event, State>>) {

	fun findTransitions(start: State, event: Event) =
		transitions.filter {
			it.start.isInstance(start) && it.event.isInstance(event)
		}

	class Row<START : State, EVENT : Event, TARGET : State>(
		val start: KClass<START>,
		val event: KClass<EVENT>,
		val target: KClass<TARGET>,
		val guard: Guard<EVENT>? = null,
		val action: Action<EVENT>? = null,
	)

	companion object {

		fun List<Row<State, Event, State>>.resolveConflict(event: Event) =
			if (count() > 1) {
				firstOrNull() { it.guard?.invoke(event) == true } ?: last()
			} else {
				first()
			}

	}
}