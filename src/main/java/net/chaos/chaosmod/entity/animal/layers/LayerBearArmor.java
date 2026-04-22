package net.chaos.chaosmod.entity.animal.layers;

import net.chaos.chaosmod.entity.animal.model.ModelBear;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class LayerBearArmor extends LayerArmorBase<ModelBear> {

	public LayerBearArmor(RenderLivingBase<?> rendererIn) {
		super(rendererIn);
	}

	@Override
	protected void initArmor() {
        this.modelLeggings = new ModelBear();
        this.modelArmor = new ModelBear();
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	protected void setModelSlotVisible(ModelBear model, EntityEquipmentSlot slotIn) {
        this.setModelVisible(model);

        switch (slotIn)
        {
            case HEAD:
                model.head.showModel = true;
                break;
            case CHEST:
                model.body.showModel = true;
                break;
            case LEGS:
                model.leg1.showModel = true;
                model.leg2.showModel = true;
                model.leg3.showModel = true; // FIXME : later (now only requires legs to display)
                model.leg4.showModel = true;
                break;
            case FEET:
                model.leg3.showModel = true;
                model.leg4.showModel = true;
        }
	}

    protected void setModelVisible(ModelBear model)
    {
        model.setVisible(false);
    }

    @Override
    public ModelBear getModelFromSlot(EntityEquipmentSlot slotIn) {
        return (this.isLegSlot(slotIn) ? this.modelLeggings : this.modelArmor);
    }
    
    private boolean isLegSlot(EntityEquipmentSlot slotIn)
    {
        return slotIn == EntityEquipmentSlot.LEGS;
    }
    
    @Override
    public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type) {
        ItemArmor item = (ItemArmor)stack.getItem();
        String texture = item.getArmorMaterial().getName();
        String domain = Reference.MODID;
        int idx = texture.indexOf(':');
        if (idx != -1)
        {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/bear/%s_layer_%d%s.png", domain, texture, (isLegSlot(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = (ResourceLocation)ARMOR_TEXTURE_RES_MAP.get(s1);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }

        return resourcelocation;
    }
}