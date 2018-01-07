package com.mr208.resdamp.common;

import com.mr208.resdamp.ResonantDamping;
import com.mr208.resdamp.common.crafting.RecipeArmorDamping;
import mcjty.deepresonance.items.ModItems;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

@EventBusSubscriber(modid = ResonantDamping.MOD_ID)
public class RDContent
{
	public static ArrayList<ItemStack> armorItemStacks = new ArrayList<>();
	
	public static ItemStack helmUpgrade;
	public static ItemStack chestUpgrade;
	public static ItemStack legUpgrade;
	public static ItemStack footUpgrade;
	
	protected static ItemStack processUpgradeItem(String item, ItemStack defaultUpgrade)
	{
		ItemStack parsedUpgrade = ItemStack.EMPTY;
		String[] entries = StringUtils.split(item,"@");
		
		Item parsedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(entries[0]));
		int parsedMeta = Integer.parseInt(entries[1]);
		
		if(parsedItem != null)
			parsedUpgrade = new ItemStack(parsedItem, 1, parsedMeta);
		
		return parsedUpgrade.isEmpty ? defaultUpgrade : parsedUpgrade;
	}
	
	@SubscribeEvent
	public static void onRecipeRegistry(RegistryEvent.Register<IRecipe> event)
	{
		ResonantDamping.LOGGER.info("Starting IRecipe RegistryEvent");
		
		helmUpgrade = processUpgradeItem(ConfigHandler.helmItem, new ItemStack(ModItems.helmet));
		chestUpgrade = processUpgradeItem(ConfigHandler.chestItem, new ItemStack(ModItems.chestplate));
		legUpgrade = processUpgradeItem(ConfigHandler.legItem, new ItemStack(ModItems.leggings));
		footUpgrade = processUpgradeItem(ConfigHandler.footItem, new ItemStack(ModItems.boots));
		
		RecipeArmorDamping helmRecipe = new RecipeArmorDamping(EntityEquipmentSlot.HEAD, helmUpgrade);
		RecipeArmorDamping chestRecipe = new RecipeArmorDamping(EntityEquipmentSlot.CHEST, chestUpgrade);
		RecipeArmorDamping legRecipe = new RecipeArmorDamping(EntityEquipmentSlot.LEGS, legUpgrade);
		RecipeArmorDamping footRecipe = new RecipeArmorDamping(EntityEquipmentSlot.FEET, footUpgrade);
		
		event.getRegistry().registerAll(helmRecipe,chestRecipe, legRecipe, footRecipe);
	
	}
}
