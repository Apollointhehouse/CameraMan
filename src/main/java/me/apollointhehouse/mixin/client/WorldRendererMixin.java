package me.apollointhehouse.mixin.client;

import me.apollointhehouse.client.events.Render3DEvent;
import me.apollointhehouse.client.utils.RenderUtils;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.camera.ICamera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.apollointhehouse.client.ClientLogic.clientBus;

@Mixin(value = WorldRenderer.class, remap = false)
public class WorldRendererMixin {
	@Unique
	RenderUtils renderUtils = new RenderUtils();

	@Inject(at = @At("TAIL"), method = "renderWorld")
	private void onEndRenderWorld(float renderPartialTicks, long updateRenderersUntil, CallbackInfo info) {
		Render3DEvent event = new Render3DEvent(renderUtils, renderPartialTicks);

		ICamera camera = ((WorldRenderer)(Object)this).mc.activeCamera;
		renderUtils.begin(camera, renderPartialTicks);
		clientBus.post(event);
		renderUtils.end();
	}
}
