package me.apollointhehouse

import me.apollointhehouse.net.client.ClientLogic
import me.apollointhehouse.net.server.ServerLogic
import me.apollointhehouse.raywire.Raywire.registry
import org.slf4j.LoggerFactory
import turniplabs.halplibe.helper.EnvironmentHelper
import turniplabs.halplibe.util.GameStartEntrypoint

object Freecam : GameStartEntrypoint {
	const val MOD_ID = "freecam"
	private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun afterGameStart() {
		if (EnvironmentHelper.isServerEnvironment()) {
			logger.info("Freecam server started.")
			registry.subscribe(ServerLogic())
		} else {
			logger.info("Freecam client started.")
			registry.subscribe(ClientLogic())
		}
	}

	override fun beforeGameStart() {}
}
