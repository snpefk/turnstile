package player

import io.github.snpefk.base.Action

object OpenDrawer : Action<OpenClose> {

	override fun invoke(event: OpenClose) {}
}

fun openDrawer(event: OpenClose) {
	println("Player open drawer")
}

object StoreCdInfo : Action<CdDetected> {

	override fun invoke(event: CdDetected) {
		println("Player store cd info: ${event.name} [${event.type}]")
	}
}

object CloseDrawer : Action<OpenClose> {

	override fun invoke(event: OpenClose) {
		println("Player close drawer")
	}
}

object StartPlayback : Action<Play> {

	override fun invoke(event: Play) {
		println("Player start playback")
	}
}

object PausePlayback : Action<Pause> {

	override fun invoke(event: Pause) {}
}

object ResumePlayback : Action<Play> {

	override fun invoke(event: Play) {}
}

object StopPlayback : Action<Stop> {

	override fun invoke(event: Stop) {}
}

object StopAndOpen : Action<OpenClose> {

	override fun invoke(event: OpenClose) {}
}