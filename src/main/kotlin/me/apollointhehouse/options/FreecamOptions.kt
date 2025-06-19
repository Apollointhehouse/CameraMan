package me.apollointhehouse.options

import net.minecraft.client.gui.options.components.BooleanOptionComponent
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.option.OptionBoolean

interface FreecamOptions {
	val freecamBind: KeyBinding
	val freecam: OptionBoolean
}
