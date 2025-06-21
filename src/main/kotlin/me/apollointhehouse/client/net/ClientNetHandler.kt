package me.apollointhehouse.client.net

import me.apollointhehouse.client.Config
import me.apollointhehouse.client.modules.Freecam
import me.apollointhehouse.core.net.NetHandler
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import net.minecraft.client.Minecraft
import net.minecraft.core.net.packet.PacketAESSendKey
import net.minecraft.core.net.packet.PacketCustomPayload
import org.slf4j.LoggerFactory

class ClientNetHandler : NetHandler {
	@EventHandler
	fun onLogin(event: PacketEvent.Receive) {
		if (event.packet !is PacketAESSendKey) return
		Config.supported = false
		Freecam.disable()

		val mc = Minecraft.getMinecraft()

		logger.debug("Sending Freecam Request to server...")
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
				logger.debug("Freecam Supported!")
				Config.supported = true
			}
			else -> {
				logger.warn("Unknown Freecam data received: ${data.toHexString()}")
				Config.supported = false
			}
		}
	}

	companion object {
		private val logger = LoggerFactory.getLogger(ClientNetHandler::class.java)
	}
}
