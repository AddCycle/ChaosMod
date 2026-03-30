package net.chaos.chaosmod.lore.dialogs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class DialogEventHandler {

	@SubscribeEvent
	public static void onRenderLivingSpecials(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
	    Entity entity = event.getEntity();
	    String dialog = null;
	    EntityPlayer player = Minecraft.getMinecraft().player;
	    if (entity.hasCustomName() || entity.getAlwaysRenderNameTag()) return;

	    if (entity.getDistanceSq(player) < 60) {
	    	if (entity instanceof ITalkable) {
	    		dialog = ((ITalkable) entity).getDialogText();
	    	} else if (entity instanceof EntityCreeper) {
	    		dialog = "Kabuuuum !!";
	    	} else if (entity instanceof EntityZombie) {
	    		dialog = "Brains...";
	    	}
	    }

	    if (dialog == null || dialog.isEmpty()) return;

	    renderDialogBox(event.getX(), event.getY(), event.getZ(), dialog, event.getEntity(), event.getPartialRenderTick());
	}
	
	public static void renderDialogBox(double x, double y, double z, String text, Entity entity, float partialTicks) {
		GlStateManager.pushMatrix();
		
        GlStateManager.alphaFunc(516, 0.1F);
        
        renderLivingLabel(entity, text, x, y, z, 10);

		GlStateManager.popMatrix();
	}

    private static <T extends Entity> void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance)
    {
		Minecraft mc = Minecraft.getMinecraft();
	    RenderManager renderManager = mc.getRenderManager();
	    
	    if (!canRenderName(entityIn)) return;

        double d0 = entityIn.getDistanceSq(renderManager.renderViewEntity);
        if (d0 <= (double)(maxDistance * maxDistance))
        {
            boolean flag = entityIn.isSneaking();
            float f = renderManager.playerViewY;
            float f1 = renderManager.playerViewX;
            boolean flag1 = renderManager.options.thirdPersonView == 2;
            float f2 = entityIn.height + 0.5F - (flag ? 0.25F : 0.0F);
            int i = "deadmau5".equals(str) ? -10 : 0;
            EntityRenderer.drawNameplate(renderManager.getFontRenderer(), str, (float)x, (float)y + f2, (float)z, i, f, f1, flag1, flag);
        }
    }
    
    private static <T extends Entity> boolean canRenderName(T entity)
    {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
        boolean flag = !entity.isInvisibleToPlayer(entityplayersp);

        if (entity != entityplayersp)
        {
            Team team = entity.getTeam();
            Team team1 = entityplayersp.getTeam();

            if (team != null)
            {
                Team.EnumVisible team$enumvisible = team.getNameTagVisibility();

                switch (team$enumvisible)
                {
                    case ALWAYS:
                        return flag;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return team1 == null ? flag : team.isSameTeam(team1) && (team.getSeeFriendlyInvisiblesEnabled() || flag);
                    case HIDE_FOR_OWN_TEAM:
                        return team1 == null ? flag : !team.isSameTeam(team1) && flag;
                    default:
                        return true;
                }
            }
        }
        
        return flag;
    }
}