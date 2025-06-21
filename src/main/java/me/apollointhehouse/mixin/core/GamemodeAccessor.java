package me.apollointhehouse.mixin.core;

import net.minecraft.core.player.gamemode.Gamemode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Gamemode.class, remap = false)
public interface GamemodeAccessor {
	@Accessor("canInteract")
	void setCanInteract(boolean canInteract);
}
