package me.apollointhehouse.mixin.client;

import me.apollointhehouse.client.events.KeyPressEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.InputDevice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.apollointhehouse.client.ClientLogic.clientRegistry;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
	@Inject(method = "checkBoundInputs", at = @At("HEAD"))
	public void checkBoundInputs(InputDevice currentInputDevice, CallbackInfoReturnable<Boolean> cir) {
		KeyPressEvent event = new KeyPressEvent(currentInputDevice);
		clientRegistry.invoke(event);
	}
}
