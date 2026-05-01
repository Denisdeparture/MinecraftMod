package com.ctfow.createthefactoryofwar;

import net.createmod.catnip.render.BindableTexture;
import net.minecraft.resources.ResourceLocation;

public class SpecialTextures implements BindableTexture {

	public static final String ASSET_PATH = "textures/";
	private final ResourceLocation location;

	public SpecialTextures(final String filename) {
		this.location = takePath(filename);
	}

	public static ResourceLocation takePath(String filename) {
		return ResourceLocation.tryBuild(CreateOfWar.MODID, ASSET_PATH + filename);
	}

	public ResourceLocation getLocation() {
		return this.location;
	}
}
