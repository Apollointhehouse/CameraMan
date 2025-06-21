package me.apollointhehouse.client.net

import me.apollointhehouse.client.ClientConfig
import me.apollointhehouse.client.modules.ModuleManager
import me.apollointhehouse.client.options.OptionsPages
import me.apollointhehouse.core.net.NetHandler
import me.apollointhehouse.logger
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import net.minecraft.client.Minecraft
import net.minecraft.core.net.packet.PacketAESSendKey
import net.minecraft.core.net.packet.PacketCustomPayload

class ClientNetHandler : NetHandler {
	@EventHandler
	fun onLogin(event: PacketEvent.Receive) {
		if (event.packet !is PacketAESSendKey) return
		OptionsPages.options.forEach { option ->
			option.value = false
			option.onUpdate()
		}
		ClientConfig.supported = false

		val mc = Minecraft.getMinecraft()

		logger.info("Sending CameraMan Request to server...")
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
				logger.info("CameraMan Supported!")
				ClientConfig.supported = true
			}
			else -> {
				logger.warn("Unknown CameraMan data received: ${data.toHexString()}")
				ClientConfig.supported = false
			}
		}
	}
}
