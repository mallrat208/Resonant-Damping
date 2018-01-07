package com.mr208.resdamp.common.crafting;

import com.mr208.resdamp.ResonantDamping;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeArmorDamping extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
	private final ItemStack dampingItemStack;
	private final EntityEquipmentSlot equipmentSlot;
	
	public RecipeArmorDamping(EntityEquipmentSlot slot, ItemStack itemStack)
	{
		this.dampingItemStack = itemStack.copy();
		this.equipmentSlot = slot;
		this.setRegistryName(ResonantDamping.MOD_ID, slot.getName());
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		boolean armor = false;
		boolean upgradeItem = false;
		
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				if (EntityLiving.getSlotForItemStack(stack) == equipmentSlot && !ResonantDamping.isAntiRadiation(stack) && !armor && !stack.isItemEqual(this.dampingItemStack))
					armor = true;
				else if (stack.isItemEqual(this.dampingItemStack) && !upgradeItem)
					upgradeItem = true;
				else
					return false;
			}
		}
		return armor && upgradeItem;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack armor = ItemStack.EMPTY;
		for (int i = 0; i<inv.getSizeInventory();i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
				if(EntityLiving.getSlotForItemStack(stack) == equipmentSlot && !stack.isItemEqual(this.dampingItemStack))
					armor = stack.copy();
		}
		
		if(!armor.isEmpty())
		{
			if(armor.hasTagCompound())
			{
				armor.getTagCompound().setBoolean(ResonantDamping.RADIATION_TAG, true);
			}
			else
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setBoolean(ResonantDamping.RADIATION_TAG, true);
				armor.setTagCompound(tag);
			}
			
			return armor;
		}
		
		return ItemStack.EMPTY;	}
	
	@Override
	public boolean canFit(int width, int height)
	{
		return width * height >= 2;
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
	{
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
		
	}
	
	@Override
	public boolean isDynamic()
	{
		return true;
	}
}
