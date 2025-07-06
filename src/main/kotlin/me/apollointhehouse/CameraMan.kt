package me.apollointhehouse

import me.apollointhehouse.client.ClientLogic
import me.apollointhehouse.server.ServerLogic
import org.slf4j.LoggerFactory
import turniplabs.halplibe.helper.EnvironmentHelper
import turniplabs.halplibe.util.GameStartEntrypoint

internal val logger = LoggerFactory.getLogger(MOD_ID)
const val MOD_ID = "cameraman"

object CameraMan : GameStartEntrypoint {
	override fun afterGameStart() {
		val logic = if (EnvironmentHelper.isServerEnvironment()) ServerLogic() else ClientLogic()

		logger.info("Starting env logic...")
		logic.run()
	}

	override fun beforeGameStart() {}
}
