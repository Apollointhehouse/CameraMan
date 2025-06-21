package me.apollointhehouse

import me.apollointhehouse.client.ClientLogic
import me.apollointhehouse.server.ServerLogic
import org.slf4j.LoggerFactory
import turniplabs.halplibe.helper.EnvironmentHelper
import turniplabs.halplibe.util.GameStartEntrypoint

object CameraMan : GameStartEntrypoint {
	const val MOD_ID = "cameraman"
	private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun afterGameStart() {
		val logic = if (EnvironmentHelper.isServerEnvironment()) ServerLogic() else ClientLogic()

		logger.debug("Starting env logic...")
		logic.run()
	}

	override fun beforeGameStart() {}
}
