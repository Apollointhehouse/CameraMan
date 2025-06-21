package me.apollointhehouse.client.net

import me.apollointhehouse.client.ClientConfig
import me.apollointhehouse.client.modules.ModuleManager
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
		ClientConfig.supported = false
		ModuleManager.disableAll()

		val mc = Minecraft.getMinecraft()

		logger.debug("Sending CameraMan Request to server...")
		mc.sendQueue.addToSendQueue(PacketCustomPayload("CameraMan", byteArrayOf(0x00)))
	}

	@OptIn(ExperimentalStdlibApi::class)
	@EventHandler
	fun onCustomPayload(event: PacketEvent.Receive) {
		val packet = event.packet as? PacketCustomPayload ?: return

		if (packet.channel != "CameraMan") return
		if (packet.data.isEmpty()) return

		when (val data = packet.data.first()) {
			0x01.toByte() -> {
				logger.debug("CameraMan Supported!")
				ClientConfig.supported = true
			}
			else -> {
				logger.warn("Unknown CameraMan data received: ${data.toHexString()}")
				ClientConfig.supported = false
			}
		}
	}

	companion object {
		private val logger = LoggerFactory.getLogger(ClientNetHandler::class.java)
	}
}
