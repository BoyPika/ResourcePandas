package com.mrbysco.resourcepandas.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrbysco.resourcepandas.entity.ResourcePandaEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class ResourceLayer<T extends ResourcePandaEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private final ResourceLocation overlayLocation;

	public ResourceLayer(RenderLayerParent<T, M> entityRendererIn, ResourceLocation overlay) {
		super(entityRendererIn);
		this.overlayLocation = overlay;
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, T resourcePanda, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (resourcePanda.isTransformed() && resourcePanda.hasResourceVariant()) {
			EntityModel<T> entityModel = this.getParentModel();
			entityModel.prepareMobModel(resourcePanda, limbSwing, limbSwingAmount, partialTicks);
			this.getParentModel().copyPropertiesTo(entityModel);
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.overlayLocation));
			entityModel.setupAnim(resourcePanda, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			String hexColor = resourcePanda.getHexColor();

			int color;
			int alpha = (int) (resourcePanda.getAlpha() * 255);
			if (resourcePanda.hasCustomName() && "jeb_".equals(resourcePanda.getName().getString())) {
				int k = resourcePanda.tickCount / 25 + resourcePanda.getId();
				int l = DyeColor.values().length;
				int i1 = k % l;
				int j1 = (k + 1) % l;
				float f = ((float) (resourcePanda.tickCount % 25) + partialTicks) / 25.0F;
				int k1 = Sheep.getColor(DyeColor.byId(i1));
				int l1 = Sheep.getColor(DyeColor.byId(j1));
				color = FastColor.ARGB32.lerp(f, k1, l1);
			} else {
				color = color(hexColor);
			}
			entityModel.renderToBuffer(poseStack, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.color(alpha, color));
		}
	}

	public int color(String hex) {
		int red = Integer.valueOf(hex.substring(1, 3), 16);
		int green = Integer.valueOf(hex.substring(3, 5), 16);
		int blue = Integer.valueOf(hex.substring(5, 7), 16);
		return FastColor.ARGB32.color(red, green, blue);
	}

	public float getRed(String hex) {
		return hex.isEmpty() ? 0.0F : (float) Integer.valueOf(hex.substring(1, 3), 16) / 255F;
	}

	public float getGreen(String hex) {
		return hex.isEmpty() ? 0.0F : (float) Integer.valueOf(hex.substring(3, 5), 16) / 255F;
	}

	public float getBlue(String hex) {
		return hex.isEmpty() ? 0.0F : (float) Integer.valueOf(hex.substring(5, 7), 16) / 255F;
	}
}
