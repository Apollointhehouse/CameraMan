package me.apollointhehouse.client

import me.apollointhehouse.client.modules.Freecam
import me.apollointhehouse.client.modules.ModuleManager
import me.apollointhehouse.client.net.ClientNetHandler
import me.apollointhehouse.client.options.OptionsPages
import me.apollointhehouse.core.EnvLogic
import me.apollointhehouse.logger
import me.apollointhehouse.raywire.Raywire.globalRegistry
import me.apollointhehouse.raywire.api.Registry

class ClientLogic : EnvLogic {
	override fun run() {
		logger.info("Running client logic...")

		logger.info("Registering client net handler...")
		globalRegistry.subscribe(ClientNetHandler())

		ModuleManager.register(Freecam())

		OptionsPages.init()
	}

	companion object {
		@JvmField val clientRegistry = Registry()
	}
}
