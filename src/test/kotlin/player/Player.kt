package player

import io.github.snpefk.base.State
import io.github.snpefk.machine.StateMachine
import io.github.snpefk.machine.row
import io.github.snpefk.machine.transitionTable

class Player(override val initialState: State = Empty) : StateMachine() {

	override val table = transitionTable {

		row<Stopped, 	Play, 		Playing>(	action = StartPlayback)
		row<Stopped, 	OpenClose, 	Open>(		action = ::openDrawer)
		row<Stopped, 	Stop, 		Stopped>()

		row<Open, 		OpenClose, 	Empty>(		action = CloseDrawer)

		row<Empty, 		OpenClose, 	Open>(		action = OpenDrawer)
		row<Empty, 		CdDetected, Stopped>(	action = StoreCdInfo, guard = GoodDiskFormat)
		row<Empty, 		CdDetected, Playing>(	action = StoreCdInfo, guard = AutoStart)

		row<Playing	, 	Stop, 		Stopped>(	action = StopPlayback)
		row<Playing, 	Pause, 		Paused>(	action = PausePlayback)
		row<Playing, 	OpenClose, 	Open>(		action = StopAndOpen)

		row<Paused, 	Play, 		Playing>(	action=ResumePlayback)
		row<Paused, 	Stop, 		Playing>(	action=StopPlayback)
		row<Paused, 	OpenClose, 	Open>(		action=StopAndOpen)
	}
}

