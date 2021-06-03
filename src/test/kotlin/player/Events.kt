package player

import io.github.snpefk.base.Event

object Play : Event
object Pause : Event
object OpenClose : Event
object Stop : Event

class CdDetected(val name: String, val type: DiskType) : Event