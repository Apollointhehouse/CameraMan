package me.apollointhehouse.client.modules

import me.apollointhehouse.client.ClientLogic.Companion.clientBus
import me.apollointhehouse.raywire.Raywire.globalBus
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.player.PlayerLocal

abstract class Module(val name: String, val keyCode: Int = -1) {
	var enabled = false
		private set

	protected val mc: Minecraft = Minecraft.getMinecraft()
	protected val player: PlayerLocal get() = mc.thePlayer

	open fun onEnable() {}
	open fun onDisable() {}

	fun disable() {
		if (!enabled) return

		enabled = false
		globalBus.unsubscribe(this)
		clientBus.unsubscribe(this)
		onDisable()
	}

	fun enable() {
		if (enabled) return

		enabled = true
		onEnable()
		globalBus.subscribe(this)
		clientBus.subscribe(this)
	}
}
