package me.apollointhehouse.net.server

import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import net.minecraft.core.net.packet.PacketCustomPayload
import net.minecraft.server.MinecraftServer
import org.slf4j.LoggerFactory

class ServerLogic {
	@OptIn(ExperimentalStdlibApi::class)
	@EventHandler
	fun onCustomPayload(event: PacketEvent.Receive) {
		val packet = event.packet as? PacketCustomPayload ?: return

		if (packet.channel != "Freecam") return
		if (packet.data.isEmpty()) return

		when (val data = packet.data.first()) {
			0x00.toByte() -> {
				logger.info("Handling Freecam Request!")
				MinecraftServer.getInstance().playerList.sendPacketToAllPlayers(
					PacketCustomPayload(
						"Freecam",
						byteArrayOf(0x01)
					)
				)
			}
			else -> {
				logger.info("Unknown Freecam data received: ${data.toHexString()}")
			}
		}
	}

	companion object {
		private val logger = LoggerFactory.getLogger(ServerLogic::class.java)
	}
}
