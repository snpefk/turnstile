package io.github.snpefk.machine

import io.github.snpefk.base.EmptyEvent
import io.github.snpefk.base.Event
import io.github.snpefk.base.State
import io.github.snpefk.machine.TransitionTable.Companion.resolveConflict
import kotlin.reflect.full.createInstance

abstract class StateMachine {

	abstract val initialState: State
	abstract val table: TransitionTable

	private var started = false
	private val history = ArrayDeque<Transition.Transited<Event>>()

	fun start() {
		initialState.onEnter(EmptyEvent)
		started = true
	}

	fun currentState(): State = history.lastOrNull()?.target ?: initialState

	fun process(event: Event): Transition {
		check(started) { "State machine is not started. Call start() method first." }

		val currentState = currentState()

		val transition = table
			.findTransitions(currentState, event)
			.takeIf { it.isNotEmpty() }
			?.resolveConflict(event)

		transition ?: return Transition.NoTransition

		if (transition.guard?.invoke(event) == false) {
			return Transition.FailedGuard
		}

		currentState.onExit(event)
		val target: State = getTargetState(transition)
		target.onEnter(event)
		transition.action?.invoke(event)

		return Transition.Transited(currentState, event, target, transition.action, transition.guard)
			.also { history.add(it) }
	}

	private fun getTargetState(transition: TransitionTable.Row<State, Event, State>): State {
		val target = transition.target
		history.asReversed().forEach {
			when {
				target.isInstance(it.target) -> return it.target
				target.isInstance(it.start)  -> return it.start
			}
		}

		return transition.target.objectInstance ?: transition.target.createInstance()
	}

	fun stop() {
		currentState().onExit(EmptyEvent)
	}
}