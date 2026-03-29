package net.chaos.chaosmod.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.chaos.chaosmod.lore.dialogs.ITalkable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySwordOfWrath extends EntityLiving implements ITalkable {
	private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager
			.<Optional<UUID>>createKey(EntitySwordOfWrath.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public EntitySwordOfWrath(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}

	@Override
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages")
				&& this.getOwner() instanceof EntityPlayerMP) {
			this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage());
		}

		super.onDeath(cause);
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
		if (own == null) return null;
		return "I'm owned by " + own.getName();
	}
}