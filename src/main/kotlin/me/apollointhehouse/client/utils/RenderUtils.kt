package me.apollointhehouse.client.utils

import me.apollointhehouse.mixin.WorldRendererAccessor
import net.minecraft.client.Minecraft
import net.minecraft.client.render.camera.ICamera
import net.minecraft.client.render.tessellator.Tessellator
import net.minecraft.core.util.helper.Color
import net.minecraft.core.util.phys.AABB
import net.minecraft.core.util.phys.Vec3
import org.lwjgl.opengl.GL11


class RenderUtils {
	private lateinit var cameraPos: Vec3

	fun begin(camera: ICamera, partialTick: Float) {
		val mc = Minecraft.getMinecraft()

		cameraPos = camera.getPosition(partialTick)

		GL11.glPushMatrix()

		GL11.glMatrixMode(GL11.GL_PROJECTION)
		GL11.glLoadIdentity()

		val rendererAccessor = mc.worldRenderer as WorldRendererAccessor

		rendererAccessor.invokeSetupCameraTransform(partialTick)

		GL11.glMatrixMode(GL11.GL_MODELVIEW)
		GL11.glLoadIdentity()

		GL11.glRotated(camera.getXRot(partialTick), 1.0, 0.0, 0.0)
		GL11.glRotated(camera.getYRot(partialTick), 0.0, 1.0, 0.0)


		GL11.glTranslated(0.0, -cameraPos.y, 0.0)


		// GL settings
		GL11.glEnable(GL11.GL_BLEND) // Allow transparent colors
		GL11.glDisable(GL11.GL_TEXTURE_2D) // Remove texture flickering
		GL11.glDisable(GL11.GL_DEPTH_TEST) // Make everything always render on top
	}

	fun end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST)
		GL11.glEnable(GL11.GL_CULL_FACE)
		GL11.glEnable(GL11.GL_TEXTURE_2D)
		GL11.glDisable(GL11.GL_BLEND)
		GL11.glColor4f(1f, 1f, 1f, 1f) // Reset the color for good measure

		GL11.glMatrixMode(GL11.GL_PROJECTION)
		GL11.glPopMatrix()
	}


	fun line(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double, width: Int, color: Color) {
		GL11.glPushMatrix()
		GL11.glColor4f(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
		GL11.glLineWidth(width.toFloat())

		val tessellator = Tessellator.instance
		tessellator.startDrawing(3)
		tessellator.addVertex(cameraPos.x - x1, y1, cameraPos.z - z1)
		tessellator.addVertex(cameraPos.x - x2, y2, cameraPos.z - z2)
		tessellator.draw()

		GL11.glPopMatrix()
	}

	fun boxOutline(
		bb: AABB,
		lineWidth: Int,
		color: Color
	) {
		// bottom base
		line(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.minZ, lineWidth, color)
		line(bb.maxX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, lineWidth, color)
		line(bb.maxX, bb.minY, bb.maxZ, bb.minX, bb.minY, bb.maxZ, lineWidth, color)
		line(bb.minX, bb.minY, bb.maxZ, bb.minX, bb.minY, bb.minZ, lineWidth, color)

		// top base
		line(bb.minX, bb.maxY, bb.minZ, bb.maxX, bb.maxY, bb.minZ, lineWidth, color)
		line(bb.maxX, bb.maxY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, lineWidth, color)
		line(bb.maxX, bb.maxY, bb.maxZ, bb.minX, bb.maxY, bb.maxZ, lineWidth, color)
		line(bb.minX, bb.maxY, bb.maxZ, bb.minX, bb.maxY, bb.minZ, lineWidth, color)

		// columns
		line(bb.minX, bb.minY, bb.minZ, bb.minX, bb.maxY, bb.minZ, lineWidth, color)
		line(bb.maxX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.minZ, lineWidth, color)
		line(bb.minX, bb.minY, bb.maxZ, bb.minX, bb.maxY, bb.maxZ, lineWidth, color)
		line(bb.maxX, bb.minY, bb.maxZ, bb.maxX, bb.maxY, bb.maxZ, lineWidth, color)
	}
}
