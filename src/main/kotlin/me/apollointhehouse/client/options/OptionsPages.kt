package me.apollointhehouse.client.options

import me.apollointhehouse.CameraMan.MOD_ID
import me.apollointhehouse.client.ClientConfig
import me.apollointhehouse.client.modules.ModuleManager
import me.apollointhehouse.client.utils.addCallback
import me.apollointhehouse.mixin.core.LanguageAccessor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.options.components.BooleanOptionComponent
import net.minecraft.client.gui.options.components.KeyBindingComponent
import net.minecraft.client.gui.options.components.OptionsCategory
import net.minecraft.client.gui.options.data.OptionsPage
import net.minecraft.client.gui.options.data.OptionsPages
import net.minecraft.client.input.InputDevice
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.option.OptionBoolean
import net.minecraft.core.block.Blocks
import net.minecraft.core.lang.I18n

object OptionsPages {
	val options = mutableListOf<OptionBoolean>()

	fun init() {
		val mc: Minecraft = Minecraft.getMinecraft()
		val settings = mc.gameSettings
		val entries = (I18n.getInstance().currentLanguage as LanguageAccessor).entries
		val modules = ModuleManager.getModules()

		val page = OptionsPage("gui.options.page.cameraman", Blocks.MOTION_SENSOR_ACTIVE.defaultStack)
		val generalCategory = OptionsCategory("gui.options.page.cameraman.category.general")
		val bindsCategory = OptionsCategory("gui.options.page.controls.category.cameraman")

		for (module in modules) {
			val optionKey = "${MOD_ID}.${module.name}.toggle"
			val option = OptionBoolean(settings, optionKey, false)

			option.addCallback {
				if (!ClientConfig.supported) return@addCallback
				if (!it.value) {
					module.disable()
					return@addCallback
				}
				module.enable()
			}
			options.add(option)

			entries["options.$optionKey"] = module.name

			generalCategory.withComponent(BooleanOptionComponent(option))

			if (module.keyCode == -1) continue // Skip modules without keybinds

			val keybindKey = "$MOD_ID.keybind.${module.name}"
			val keybind = KeyBinding(keybindKey)
				.setDefault(InputDevice.keyboard, module.keyCode)

			keybind.addCallback {
				if (!ClientConfig.supported) return@addCallback
				option.toggle()
				option.onUpdate()
			}

			entries[keybindKey] = module.name

			bindsCategory.withComponent(KeyBindingComponent(keybind))
		}

		page.withComponent(generalCategory)
		OptionsPages.register(page)
		OptionsPages.CONTROLS.withComponent(bindsCategory)
	}
}
