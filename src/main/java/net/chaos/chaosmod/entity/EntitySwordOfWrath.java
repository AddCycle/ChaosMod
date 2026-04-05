package net.chaos.chaosmod.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import net.chaos.chaosmod.entity.ai.sword.EntityAIAttackTarget;
import net.chaos.chaosmod.entity.ai.sword.EntityAIFollowPlayer;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.lore.dialogs.ITalkable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySwordOfWrath extends EntityCreature implements ITalkable {
	private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager
			.<Optional<UUID>>createKey(EntitySwordOfWrath.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	private static final DataParameter<Integer> ATTACK_TICKS = EntityDataManager.createKey(EntitySwordOfWrath.class,
			DataSerializers.VARINT);

	public EntitySwordOfWrath(World worldIn) {
		super(worldIn);
		setNoGravity(true);
		this.noClip = true;
	}

	@Override
	protected PathNavigate createNavigator(World worldIn) {
		return new PathNavigateFlying(this, worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
		this.dataManager.register(ATTACK_TICKS, 0);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAIAttackTarget(this));
		this.tasks.addTask(1, new EntityAIFollowPlayer(this, 1.0D));

		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10,
				false, false, new Predicate<EntityLiving>() {
					public boolean apply(@Nullable EntityLiving entityLiving) {
						return entityLiving != null && entityLiving instanceof IMob;
					}
				}));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!world.isRemote) {
			if (getAttackTicks() > 0) setAttackTicks(getAttackTicks() - 1);
		}

		// When player disconnected from world already
		if (!world.isRemote && getOwnerId() != null) {
			if (world.getPlayerEntityByUUID(getOwnerId()) == null)
				setDead();
		}
	}

	public void attack() {
		setAttackTicks(10);
	}

	@Override
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages")
				&& this.getOwner() instanceof EntityPlayerMP) {
			this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage());
			if (this.getOwner().getEntityData().hasKey("sword_of_wrath_cast") && getOwner().getEntityData().getBoolean("sword_of_wrath_cast")) {
			((EntityPlayerMP) this.getOwner()).addItemStackToInventory(new ItemStack(ModItems.SWORD_OF_WRATH_CASTER));
			((EntityPlayerMP) this.getOwner()).getEntityData().setBoolean("sword_of_wrath_cast", false);
			}
		}

		super.onDeath(cause);
	}

	public int getAttackTicks() { return this.dataManager.get(ATTACK_TICKS); }

	public void setAttackTicks(int amount) {
		if (amount < 0)
			return;
		this.dataManager.set(ATTACK_TICKS, amount);
	}

	@Nullable
	public UUID getOwnerId() { return (UUID) ((Optional<?>) this.dataManager.get(OWNER_UNIQUE_ID)).orNull(); }

	public void setOwnerId(@Nullable UUID p_184754_1_) {
		this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
	}

	@Nullable
	public EntityLivingBase getOwner() {
		try {
			UUID uuid = this.getOwnerId();
			return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean isOwner(EntityLivingBase entityIn) {
		return entityIn == this.getOwner();
	}

	@Override
	public String getDialogText() {
		EntityLivingBase own = this.getOwner();
		if (own == null)
			return null;
		return "I'm owned by " + own.getName();
	}
}