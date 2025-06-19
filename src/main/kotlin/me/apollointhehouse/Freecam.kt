package me.apollointhehouse

import me.apollointhehouse.options.FreecamOptions
import me.apollointhehouse.raywire.Raywire
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import net.fabricmc.api.ModInitializer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.options.components.BooleanOptionComponent
import net.minecraft.client.gui.options.data.OptionsPages
import net.minecraft.core.net.packet.PacketMovePlayer
import net.minecraft.core.player.gamemode.Gamemode
import net.minecraft.core.util.phys.Vec3
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import turniplabs.halplibe.util.ClientStartEntrypoint

object Freecam : ModInitializer, ClientStartEntrypoint {
	const val MOD_ID: String = "freecam"
	@JvmField val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	private val options by lazy { Minecraft.getMinecraft().gameSettings as FreecamOptions }
	private var toggled = false
	private var oldPos = Vec3.getPermanentVec3(0.0,0.0,0.0)
	private var oldGamemode = Gamemode.spectator

	override fun onInitialize() {
		LOGGER.info("Freecam initialized.")

		Raywire.registry.subscribe(this)
	}

	override fun beforeClientStart() {
	}

	override fun afterClientStart() {
		options.freecam.addCallback { option ->
			val mc = Minecraft.getMinecraft()
			val player = mc.thePlayer

			if (!option.value) {
				player.gamemode = oldGamemode
				player.moveTo(oldPos.x,oldPos.y,oldPos.z, player.xRot, player.yRot)

				return@addCallback
			}

			oldGamemode = player.gamemode
			oldPos = Vec3.getPermanentVec3(player.x, player.bb.minY, player.z)
			mc.thePlayer.gamemode = Gamemode.spectator
		}

		OptionsPages.GENERAL.withComponent(BooleanOptionComponent(options.freecam))
	}


	@EventHandler
	fun onSendPacket(event: PacketEvent.Send) {
		if (event.packet !is PacketMovePlayer) return
		if (!toggled) return

		event.cancel()
	}
}
