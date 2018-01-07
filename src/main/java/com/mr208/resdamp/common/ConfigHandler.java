package com.mr208.resdamp.common;

import net.minecraftforge.common.config.Config;

import static com.mr208.resdamp.ResonantDamping.MOD_ID;

@Config(modid = MOD_ID, name = "ResonantDamping")
public class ConfigHandler
{
	
	@Config.Comment("The item required to add Resonant Damping to head armor")
	public static String helmItem= "deepresonance:helmet@0";
	
	@Config.Comment("The item required to add Resonant Damping to chest armor")
	public static String chestItem ="deepresonance:chest@0";
	
	@Config.Comment("The item required to add Resonant Damping to leg armor")
	public static String legItem = "deepresonance:leggings@0";
	
	@Config.Comment("The item required to add Resonant Damping to foot armor")
	public static String footItem = "deepresonance:boots@0";
	
}

