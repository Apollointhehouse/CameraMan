@file:OptIn(ExperimentalStdlibApi::class)
package me.apollointhehouse.modules

import me.apollointhehouse.mixin.GamemodeAccessor
import me.apollointhehouse.options.FreecamOptions
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import net.minecraft.client.gui.options.components.BooleanOptionComponent
import net.minecraft.client.gui.options.data.OptionsPages
import net.minecraft.core.net.packet.PacketMovePlayer
import net.minecraft.core.util.phys.Vec3
import org.slf4j.LoggerFactory

object FreecamMod : Module() {
	private var oldPos = Vec3.getPermanentVec3(0.0,0.0,0.0)

	init {
		val options = mc.gameSettings as FreecamOptions

		options.freecam.addCallback { option ->
			if (!option.value) {
				disable()
				return@addCallback
			}
			enable()
		}

		OptionsPages.GENERAL.withComponent(BooleanOptionComponent(options.freecam))
	}

	override fun onEnable() {
		oldPos = Vec3.getPermanentVec3(player.x, player.bb.minY, player.z)
		player.noPhysics = true
		(player.gamemode as GamemodeAccessor).setCanInteract(false)
	}

	override fun onDisable() {
		player.moveTo(oldPos.x,oldPos.y,oldPos.z, 0f, 0f)

		player.noPhysics = false
		(player.gamemode as GamemodeAccessor).setCanInteract(true)
	}

	@EventHandler
	fun onSendPacket(event: PacketEvent.Send) {
		if (event.packet is PacketMovePlayer) event.cancel()
	}

	private val logger = LoggerFactory.getLogger(FreecamMod::class.java)
}
