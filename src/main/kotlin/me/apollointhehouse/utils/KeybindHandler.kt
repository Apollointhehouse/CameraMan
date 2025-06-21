package me.apollointhehouse.utils

import me.apollointhehouse.events.KeyPressEvent
import me.apollointhehouse.raywire.api.EventHandler
import net.minecraft.client.option.KeyBinding

class KeybindHandler(val bind: KeyBinding, val callback: (KeyBinding) -> Unit) {
	@EventHandler
	fun onKeyPress(event: KeyPressEvent) {
		val inputDevice = event.inputDevice

		if (bind.isPressEvent(inputDevice)) callback(bind)
	}
}
