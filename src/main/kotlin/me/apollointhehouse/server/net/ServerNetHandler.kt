package me.apollointhehouse.server.net

import me.apollointhehouse.core.net.NetHandler
import me.apollointhehouse.logger
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import net.minecraft.core.net.packet.PacketCustomPayload
import net.minecraft.server.MinecraftServer

class ServerNetHandler : NetHandler {
	@OptIn(ExperimentalStdlibApi::class)
	@EventHandler
	fun onCustomPayload(event: PacketEvent.Receive) {
		val packet = event.packet as? PacketCustomPayload ?: return

		if (packet.channel != "CameraMan") return
		if (packet.data.isEmpty()) return

		when (val data = packet.data.first()) {
			0x00.toByte() -> {
				logger.info("Handling CameraMan Request!")
				MinecraftServer.getInstance().playerList.sendPacketToAllPlayers(
					PacketCustomPayload(
						"CameraMan",
						byteArrayOf(0x01)
					)
				)
			}
			else -> {
				logger.warn("Unknown CameraMan data received: ${data.toHexString()}")
			}
		}
	}
}
