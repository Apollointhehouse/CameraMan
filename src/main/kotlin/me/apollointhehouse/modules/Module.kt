package me.apollointhehouse.modules

import me.apollointhehouse.raywire.Raywire.registry
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.player.PlayerLocal

abstract class Module {
	var enabled = false
		private set

	protected val mc: Minecraft = Minecraft.getMinecraft()
	protected val player: PlayerLocal get() = mc.thePlayer

	open fun onEnable() {}
	open fun onDisable() {}

	fun disable() {
		if (!enabled) return

		enabled = false
		registry.unsubscribe(this)
		onDisable()
	}

	fun enable() {
		if (enabled) return

		enabled = true
		onEnable()
		registry.subscribe(this)
	}
}
