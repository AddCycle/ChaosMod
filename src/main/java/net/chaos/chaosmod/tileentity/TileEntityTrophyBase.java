package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModPotions;
import net.chaos.chaosmod.inventory.TrophyContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import util.Reference;

public class TileEntityTrophyBase extends TileEntityLockableLoot implements ITickable,IInventory {
	public int variant;
	public double range;
	public int particles;
	public Potion potion;
	public EnumParticleTypes particleType;
	private NonNullList<ItemStack> content = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
	
	public TileEntityTrophyBase(int variant) {
		this(4, 30, ModPotions.POTION_VIKING, EnumParticleTypes.REDSTONE, variant);
	}

	public TileEntityTrophyBase(double range, int particles, Potion potion, EnumParticleTypes particleTypes, int variant) {
		this.range = range;
		this.particles = particles;
		this.potion = potion;
		particleType = particleTypes;
	}

	@Override
	public void update() {
		spawnParticleCircle(world, pos, range, particles, particleType);
		applyEffectBasedOnRange(range, new PotionEffect(potion, 10, 0));
	}

	private void applyEffectBasedOnRange(double range, PotionEffect effectIn) {
		if (world.isRemote) return;
		for (EntityPlayer pl : this.world.playerEntities) {
			double dist = Math.sqrt(pl.getDistanceSqToCenter(pos));
			// System.out.println(dist);
			if (dist <= range) {
				pl.addPotionEffect(effectIn);
			}
		}
	}

	public void spawnParticleCircle(World world, BlockPos center, double radius, int particleCount, EnumParticleTypes particle) {
	    double cx = center.getX() + 0.5;
	    double cy = center.getY() + 0.5;
	    double cz = center.getZ() + 0.5;

	    for (int i = 0; i < particleCount; i++) {
	        double angle = 2 * Math.PI * i / particleCount;
	        double x = cx + radius * Math.cos(angle);
	        double z = cz + radius * Math.sin(angle);
	        world.spawnParticle(particle, x, cy, z, 0, 0, 0);
	    }
	}

	@Override
	public String getName() {
		return new TextComponentTranslation("alz.container.name").getFormattedText();
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.content) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return content.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
        this.fillWithLoot((EntityPlayer)null);
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.getItems(), index, count);
        return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.fillWithLoot((EntityPlayer)null);
        this.getItems().set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		player.openGui(Main.instance, Reference.GUI_FURNACE_ID, world, 0, 0, 0);
	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
		content.clear();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new TrophyContainerBase(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return "chaosmod:trophy_gui";
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return content;
	}
}
