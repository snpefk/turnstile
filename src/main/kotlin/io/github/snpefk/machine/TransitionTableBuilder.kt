package io.github.snpefk.machine

import io.github.snpefk.base.Action
import io.github.snpefk.base.Event
import io.github.snpefk.base.Guard
import io.github.snpefk.base.State
import io.github.snpefk.machine.TransitionTable

class TransitionTableBuilder {

	private val rows: MutableList<TransitionTable.Row<State, Event, State>> = mutableListOf()

	fun <S : State, E : Event, T : State> add(row: TransitionTable.Row<S, E, T>) {
		rows.add(row as TransitionTable.Row<State, Event, State>)
	}

	fun build(): TransitionTable = TransitionTable(rows)
}

inline fun <reified START : State, reified EVENT : Event, reified TARGET : State> TransitionTableBuilder.row(
	guard: Guard<EVENT>? = null,
	action: Action<EVENT>? = null
) {

	val row = TransitionTable.Row(START::class, EVENT::class, TARGET::class, guard, action)
	add(row)
}

fun transitionTable(block: TransitionTableBuilder.() -> Unit): TransitionTable {
	val builder = TransitionTableBuilder()
	block(builder)
	return builder.build()
}