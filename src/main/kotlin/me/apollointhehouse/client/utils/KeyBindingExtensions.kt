package me.apollointhehouse.client.utils

import me.apollointhehouse.client.ClientLogic.Companion.clientBus
import net.minecraft.client.option.KeyBinding

fun KeyBinding.addCallback(callback: (KeyBinding) -> Unit) =
	clientBus.subscribe(KeybindHandler(this, callback))
