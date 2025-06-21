package me.apollointhehouse.mixin.client;

import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = WorldRenderer.class, remap = false)
public interface WorldRendererAccessor {
	@Invoker("setupCameraTransform")
	void invokeSetupCameraTransform(float partialTick);
}
