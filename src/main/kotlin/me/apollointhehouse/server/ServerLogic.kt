package me.apollointhehouse.server

import me.apollointhehouse.core.EnvLogic
import me.apollointhehouse.raywire.Raywire.registry
import me.apollointhehouse.server.net.ServerNetHandler
import org.slf4j.LoggerFactory

class ServerLogic : EnvLogic {
	override fun run() {
		logger.debug("Running server logic...")

		logger.debug("Registering server net handler...")
		registry.subscribe(ServerNetHandler())
	}

	companion object {
		private val logger = LoggerFactory.getLogger(ServerLogic::class.java)
	}
}
