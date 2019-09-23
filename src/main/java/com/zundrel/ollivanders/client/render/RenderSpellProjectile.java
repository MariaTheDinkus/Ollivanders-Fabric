package com.zundrel.ollivanders.client.render;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.zundrel.ollivanders.common.entity.EntitySpellProjectile;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RenderSpellProjectile extends EntityRenderer<EntitySpellProjectile> {
    
    private static final Identifier EXPERIENCE_ORB_TEXTURES = new Identifier("textures/entity/experience_orb.png");
    
    public RenderSpellProjectile(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
        this.field_4673 = 0.15F;
        this.field_4672 = 0.75F;
    }
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntitySpellProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if(!this.renderOutlines && entity.getSpell() != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef((float) x, (float) y, (float) z);
            this.bindEntityTexture(entity);
            GuiLighting.enable();
            int i = 10;
            float f = (float) (i % 4 * 16) / 64.0F;
            float f1 = (float) (i % 4 * 16 + 16) / 64.0F;
            float f2 = (float) (i / 4 * 16) / 64.0F;
            float f3 = (float) (i / 4 * 16 + 16) / 64.0F;
            int j = entity.getLightmapCoordinates();
            int k = j % 65536;
            int l = j / 65536;
            GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) k, (float) l);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float f9 = ((float) entity.getSpell().getColor()) / 2.0F;
            l = (int) ((MathHelper.sin(f9 + 1) + 1.0F) * 255);
            int j1 = 0xFFFFFF;//(int) ((MathHelper.sin(f9 + 4.1887903F) + 1.0F) * 255.0F);
            GlStateManager.translatef(0.0F, 0.1F, 0.0F);
            GlStateManager.rotatef(180.0F - this.renderManager.cameraPitch, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef((float) (this.renderManager.gameOptions != null && this.renderManager.gameOptions.perspective == 2 ? -1 : 1) * -this.renderManager.cameraYaw, 1.0F, 0.0F, 0.0F);
            GlStateManager.scalef(0.3F, 0.3F, 0.3F);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBufferBuilder();
            vertexbuffer.begin(7, VertexFormats.POSITION_UV_COLOR_NORMAL);
            vertexbuffer.vertex(-1, -1, 0.0D).texture((double) f, (double) f3).color(l, 255, j1, 128).normal(0.0F, 1.0F, 0.0F).end();
            vertexbuffer.vertex(1, -1, 0.0D).texture((double) f1, (double) f3).color(l, 255, j1, 128).normal(0.0F, 1.0F, 0.0F).end();
            vertexbuffer.vertex(1, 1, 0.0D).texture((double) f1, (double) f2).color(l, 255, j1, 128).normal(0.0F, 1.0F, 0.0F).end();
            vertexbuffer.vertex(-1, 1, 0.0D).texture((double) f, (double) f2).color(l, 255, j1, 128).normal(0.0F, 1.0F, 0.0F).end();
            tessellator.draw();
            GlStateManager.disableBlend();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    @Override
    protected Identifier getTexture(EntitySpellProjectile entitySpellProjectile) {
        return EXPERIENCE_ORB_TEXTURES;
    }
}