package me.apollointhehouse.mixin;

import me.apollointhehouse.options.FreecamOptions;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements FreecamOptions {
	@Unique
	KeyBinding freecamBind = new KeyBinding("key.freecam.toggle").setDefault(InputDevice.keyboard, Keyboard.KEY_Y);

	@Unique
	OptionBoolean freecam = new OptionBoolean((GameSettings)(Object)this, "key.freecam.toggle", false);

	@Override
	public @NotNull KeyBinding getFreecamBind() {
		return freecamBind;
	}


	@Override
	public @NotNull OptionBoolean getFreecam() {
		return freecam;
	}
}
