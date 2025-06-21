package me.apollointhehouse.client.utils

import me.apollointhehouse.raywire.Raywire.registry
import net.minecraft.client.option.KeyBinding

fun KeyBinding.addCallback(callback: (KeyBinding) -> Unit) =
	registry.subscribe(KeybindHandler(this, callback))
