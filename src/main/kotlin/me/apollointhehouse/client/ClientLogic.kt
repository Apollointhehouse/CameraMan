package me.apollointhehouse.client

import me.apollointhehouse.client.modules.Freecam
import me.apollointhehouse.client.modules.Module
import me.apollointhehouse.client.modules.ModuleManager
import me.apollointhehouse.client.net.ClientNetHandler
import me.apollointhehouse.client.options.OptionsPages
import me.apollointhehouse.core.EnvLogic
import me.apollointhehouse.raywire.Raywire.globalRegistry
import me.apollointhehouse.raywire.api.Registry
import org.slf4j.LoggerFactory

class ClientLogic : EnvLogic {
	override fun run() {
		logger.debug("Running client logic...")

		logger.debug("Registering client net handler...")
		globalRegistry.subscribe(ClientNetHandler())

		ModuleManager.register(Freecam)

		OptionsPages.init()

	}

	companion object {
		private val logger = LoggerFactory.getLogger(ClientLogic::class.java)
		@JvmField val clientRegistry = Registry()
	}
}
