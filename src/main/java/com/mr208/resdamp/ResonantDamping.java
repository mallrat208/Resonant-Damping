package com.mr208.resdamp;

import com.mr208.resdamp.common.CommonProxy;
import com.mr208.resdamp.common.RDContent;
import mcjty.deepresonance.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

@Mod(modid = ResonantDamping.MOD_ID, name = ResonantDamping.MOD_NAME, version =  ResonantDamping.MOD_VER, dependencies =  ResonantDamping.MOD_DEPS)
public class ResonantDamping
{
	public static final String MOD_ID = "resdamp";
	public static final String MOD_NAME = "Resonant Damping";
	public static final String MOD_VER = "1.0.0";
	public static final String MOD_DEPS = "required-after:deepresonance";
	
	private static final String PROXY_CLIENT = "com.mr208.resdamp.client.ClientProxy";
	private static final String PROXY_COMMON = "com.mr208.resdamp.common.CommonProxy";
	
	public static final String RADIATION_TAG = "AntiRadiationArmor";
	
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	@SidedProxy(clientSide = PROXY_CLIENT, serverSide = PROXY_COMMON )
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public static void onPostInit(FMLPostInitializationEvent event)
	{
		ResonantDamping.LOGGER.info("Searching for Armor");
		
		Iterator it = Item.REGISTRY.iterator();
		
		while(it.hasNext())
		{
			Item item = (Item)it.next();
			
			if(EntityLiving.getSlotForItemStack(new ItemStack(item)).getSlotType() == EntityEquipmentSlot.Type.ARMOR)
				if(!item.getRegistryName().getResourceDomain().equals("deepresonance"))
					RDContent.armorItemStacks.add(new ItemStack(item));
		}
	}
	
	public static CreativeTabs creativeTabs = new CreativeTabs(MOD_ID)
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModItems.helmet);
		}
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_)
		{
			super.displayAllRelevantItems(p_78018_1_);
			
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setBoolean(RADIATION_TAG, true);
			
			for(ItemStack armorStack : RDContent.armorItemStacks)
			{
				armorStack.setTagCompound(tagCompound);
				p_78018_1_.add(armorStack);
			}
		}
	};
	
	
	public static boolean isAntiRadiation(ItemStack armor)
	{
		if(armor.hasTagCompound() && armor.getTagCompound().hasKey(RADIATION_TAG))
		{
			return armor.getTagCompound().getBoolean(RADIATION_TAG);
		}
			
		return false;
	}
}
