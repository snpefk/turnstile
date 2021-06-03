import io.github.snpefk.machine.Transition
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import player.CdDetected
import player.DiskType
import player.Empty
import player.Open
import player.OpenClose
import player.Play
import player.Player
import player.Playing

class MainTest {

	companion object {

		const val DISK_NAME = "Romantic collection"
	}

	@Test
	fun play() {
		val player = Player()
		player.start()
		assertEquals(player.currentState(), Empty)

		val events = arrayOf(
			{ player.process(CdDetected(DISK_NAME, DiskType.BLU_RAY)) },
			{ player.process(Play) }
		)

		val result = events.all { it().isValidTransaction() }
		assertTrue(result)
	}

	@Test
	fun stop() {
		val player = Player()
		player.start()
		assertEquals(player.currentState(), Empty)

		player.process(CdDetected(DISK_NAME, DiskType.BLU_RAY))
		player.process(Play)

		player.stop()
		val currentState = player.currentState()
		assertTrue(currentState is Playing)
	}

	@Test
	fun changeDisks() {
		val player = Player()
		player.start()
		assertEquals(player.currentState(), Empty)

		player.process(CdDetected(DISK_NAME, DiskType.BLU_RAY)) // move to Stopped
		player.process(Play) // move to Playing

		val count = (player.currentState() as Playing).playCount
		assertEquals("First check failed", 1u, count)

		player.process(OpenClose) // открываем
		assertTrue("${player.currentState()}", player.currentState() is Open)

		player.process(OpenClose) // закрываем
		assertTrue("${player.currentState()}", player.currentState() is Empty)

		player.process(CdDetected("Duck Tales", DiskType.BLU_RAY))
		player.process(Play)

		val currentState = player.currentState()
		val playing2 = (currentState as Playing).playCount
		assertEquals("Second check failed", 2u, playing2)
	}

	private fun Transition.isValidTransaction(): Boolean =
		this is Transition.Transited<*>
}