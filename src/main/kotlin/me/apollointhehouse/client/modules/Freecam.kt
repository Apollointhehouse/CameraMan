@file:OptIn(ExperimentalStdlibApi::class)

package me.apollointhehouse.client.modules

import me.apollointhehouse.client.events.Render3DEvent
import me.apollointhehouse.mixin.core.GamemodeAccessor
import me.apollointhehouse.raywire.api.EventHandler
import me.apollointhehouse.raywire.api.event.core.network.PacketEvent
import net.minecraft.core.net.packet.PacketMovePlayer
import net.minecraft.core.util.helper.Color
import net.minecraft.core.util.phys.AABB
import net.minecraft.core.util.phys.Vec3
import org.lwjgl.input.Keyboard

class Freecam : Module(
	name = "Freecam",
	keyCode = Keyboard.KEY_Y
) {
	private var pos = Vec3.getPermanentVec3(0.0,0.0,0.0)

	override fun onEnable() {
		pos = Vec3.getPermanentVec3(player.x, player.bb.minY, player.z)
		player.noPhysics = true
		(player.gamemode as GamemodeAccessor).setCanInteract(false)
	}

	override fun onDisable() {
		player.moveTo(pos.x,pos.y,pos.z, player.yRot, player.xRot)
		player.xd = 0.0
		player.yd = 0.0
		player.zd = 0.0

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
}
