package player

import io.github.snpefk.base.Guard

object GoodDiskFormat : Guard<CdDetected> {

	// for test purposes player can work only with Blu-ray discs
	override fun invoke(event: CdDetected): Boolean =
		event.type == DiskType.BLU_RAY

}

object AutoStart : Guard<CdDetected> {

	override fun invoke(event: CdDetected): Boolean = false
}