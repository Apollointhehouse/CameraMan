package me.apollointhehouse.client

import me.apollointhehouse.core.EnvLogic
import me.apollointhehouse.client.modules.FreecamMod
import me.apollointhehouse.client.net.ClientNetHandler
import me.apollointhehouse.client.options.FreecamOptions
import me.apollointhehouse.client.utils.addCallback
import me.apollointhehouse.raywire.Raywire.globalRegistry
import me.apollointhehouse.raywire.api.Registry
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.options.components.BooleanOptionComponent
import net.minecraft.client.gui.options.components.KeyBindingComponent
import net.minecraft.client.gui.options.data.OptionsPages
import org.slf4j.LoggerFactory

class ClientLogic : EnvLogic {
	override fun run() {
		logger.debug("Running client logic...")

		logger.debug("Registering client net handler...")
		globalRegistry.subscribe(ClientNetHandler())
		createOptions()
	}

	private fun createOptions() {
		val mc: Minecraft = Minecraft.getMinecraft()
		val options = mc.gameSettings as FreecamOptions

		options.freecam.addCallback { option ->
			if (!Config.supported) return@addCallback
			if (!option.value) {
				FreecamMod.disable()
				return@addCallback
			}
			FreecamMod.enable()
		}

		options.freecamBind.addCallback {
			if (!Config.supported) return@addCallback

			options.freecam.toggle()
			options.freecam.onUpdate()
		}

		OptionsPages.GENERAL.withComponent(BooleanOptionComponent(options.freecam))
		OptionsPages.CONTROLS.withComponent(KeyBindingComponent(options.freecamBind))
	}

	companion object {
		private val logger = LoggerFactory.getLogger(ClientLogic::class.java)
		@JvmField val clientRegistry = Registry()
	}
}
