@file:OptIn(ExperimentalStdlibApi::class)
package me.apollointhehouse.modules

import me.apollointhehouse.events.Render3DEvent
import me.apollointhehouse.mixin.GamemodeAccessor
import me.apollointhehouse.options.FreecamOptions
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import me.apollointhehouse.utils.addCallback
import net.minecraft.client.gui.options.components.BooleanOptionComponent
import net.minecraft.client.gui.options.components.KeyBindingComponent
import net.minecraft.client.gui.options.data.OptionsPages
import net.minecraft.core.net.packet.PacketMovePlayer
import net.minecraft.core.util.helper.Color
import net.minecraft.core.util.phys.AABB
import net.minecraft.core.util.phys.Vec3
import org.slf4j.LoggerFactory

object FreecamMod : Module() {
	private var pos = Vec3.getPermanentVec3(0.0,0.0,0.0)

	init {
		val options = mc.gameSettings as FreecamOptions

		options.freecam.addCallback { option ->
			if (!option.value) {
				disable()
				return@addCallback
			}
			enable()
		}

		options.freecamBind.addCallback {
			options.freecam.toggle()
			options.freecam.onUpdate()
		}

		OptionsPages.GENERAL.withComponent(BooleanOptionComponent(options.freecam))
		OptionsPages.CONTROLS.withComponent(KeyBindingComponent(options.freecamBind))
	}

	override fun onEnable() {
		pos = Vec3.getPermanentVec3(player.x, player.bb.minY, player.z)
		player.noPhysics = true
		(player.gamemode as GamemodeAccessor).setCanInteract(false)
	}

	override fun onDisable() {
		player.moveTo(pos.x,pos.y,pos.z, player.yRot, player.xRot)

		player.noPhysics = false
		(player.gamemode as GamemodeAccessor).setCanInteract(true)
	}

	@EventHandler
	fun onSendPacket(event: PacketEvent.Send) {
		if (event.packet is PacketMovePlayer) event.cancel()
	}

	@EventHandler
	fun onRender3D(event: Render3DEvent) {
		val player = mc.thePlayer ?: return
		val width = player.bbWidth
		val height = player.bbHeight
		val x = pos.x - width / 2
		val y = pos.y
		val z = pos.z - width / 2

		val bb = AABB.getPermanentBB(x, y, z, x + width, y + height, z + width)

		event.renderUtils.boxOutline(bb, 2, Color().setRGB(0,0,255))
	}

	private val logger = LoggerFactory.getLogger(FreecamMod::class.java)
}
