package me.apollointhehouse.client.utils

import me.apollointhehouse.client.ClientLogic.Companion.clientRegistry
import net.minecraft.client.option.KeyBinding

fun KeyBinding.addCallback(callback: (KeyBinding) -> Unit) =
	clientRegistry.subscribe(KeybindHandler(this, callback))
