package me.apollointhehouse.client

import me.apollointhehouse.client.modules.Freecam
import me.apollointhehouse.client.modules.ModuleManager
import me.apollointhehouse.client.net.ClientNetHandler
import me.apollointhehouse.client.options.OptionsPages
import me.apollointhehouse.core.EnvLogic
import me.apollointhehouse.logger
import me.apollointhehouse.raywire.Raywire.globalBus
import me.apollointhehouse.raywire.api.Bus

class ClientLogic : EnvLogic {
	override fun run() {
		logger.info("Running client logic...")

		logger.info("Registering client net handler...")
		globalBus.subscribe(ClientNetHandler())

		ModuleManager.register(Freecam())

		OptionsPages.init()
	}

	companion object {
		@JvmField val clientBus = Bus()
	}
}
