package me.apollointhehouse.net.client

import me.apollointhehouse.modules.FreecamMod
import me.apollointhehouse.options.FreecamOptions
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import me.apollointhehouse.utils.addCallback
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.options.components.BooleanOptionComponent
import net.minecraft.client.gui.options.components.KeyBindingComponent
import net.minecraft.client.gui.options.data.OptionsPages
import net.minecraft.core.net.packet.PacketAESSendKey
import net.minecraft.core.net.packet.PacketCustomPayload
import org.slf4j.LoggerFactory

class ClientLogic {
	val mc = Minecraft.getMinecraft()
	val options = mc.gameSettings as FreecamOptions
	var supported = false

	init {
		options.freecam.addCallback { option ->
			if (!supported) return@addCallback
			if (!option.value) {
				FreecamMod.disable()
				return@addCallback
			}
			FreecamMod.enable()
		}

		options.freecamBind.addCallback {
			if (!supported) return@addCallback

			options.freecam.toggle()
			options.freecam.onUpdate()
		}

		OptionsPages.GENERAL.withComponent(BooleanOptionComponent(options.freecam))
		OptionsPages.CONTROLS.withComponent(KeyBindingComponent(options.freecamBind))
	}

	@EventHandler
	fun onLogin(event: PacketEvent.Receive) {
		if (event.packet !is PacketAESSendKey) return
		supported = false
		FreecamMod.disable()

		val mc = Minecraft.getMinecraft()

		logger.info("Sending Freecam Request to server...")
		mc.sendQueue.addToSendQueue(PacketCustomPayload("Freecam", byteArrayOf(0x00)))
	}

	@OptIn(ExperimentalStdlibApi::class)
	@EventHandler
	fun onCustomPayload(event: PacketEvent.Receive) {
		val packet = event.packet as? PacketCustomPayload ?: return

		if (packet.channel != "Freecam") return
		if (packet.data.isEmpty()) return

		when (val data = packet.data.first()) {
			0x01.toByte() -> {
				logger.info("Freecam Supported!")
				supported = true
			}
			else -> {
				logger.info("Unknown Freecam data received: ${data.toHexString()}")
				supported = false
			}
		}
	}

	companion object {
		private val logger = LoggerFactory.getLogger(ClientLogic::class.java)
	}
}
