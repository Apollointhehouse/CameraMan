package me.apollointhehouse.client.modules

import me.apollointhehouse.client.ClientLogic.Companion.clientRegistry
import me.apollointhehouse.raywire.Raywire.globalRegistry
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
		globalRegistry.unsubscribe(this)
		clientRegistry.unsubscribe(this)
		onDisable()
	}

	fun enable() {
		if (enabled) return

		enabled = true
		onEnable()
		globalRegistry.subscribe(this)
		clientRegistry.subscribe(this)
	}
}
