package player

import io.github.snpefk.base.Event
import io.github.snpefk.base.State

object Empty : State
object Open : State
object Stopped : State
object Paused : State
class Playing : State {

	var playCount: UInt = 0u

	override fun onEnter(e: Event) {
		playCount += 1u
	}
}
