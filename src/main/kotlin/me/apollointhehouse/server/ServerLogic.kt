package me.apollointhehouse.server

import me.apollointhehouse.core.EnvLogic
import me.apollointhehouse.logger
import me.apollointhehouse.raywire.Raywire.globalRegistry
import me.apollointhehouse.raywire.api.Registry
import me.apollointhehouse.server.net.ServerNetHandler

class ServerLogic : EnvLogic {
	override fun run() {
		logger.info("Running server logic...")

		logger.info("Registering server net handler...")
		globalRegistry.subscribe(ServerNetHandler())
	}

	companion object {
		val serverRegistry = Registry()
	}
}
