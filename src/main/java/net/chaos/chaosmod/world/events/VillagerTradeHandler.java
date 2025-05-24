package net.chaos.chaosmod.world.events;

public class VillagerTradeHandler {

    /*public static void onRegisterTrades() {
    	System.out.println("Post init villagers trades");
    	List<VillagerCareer> careers = ObfuscationReflectionHelper.getPrivateValue(
    	    VillagerProfession.class,
    	    VillagerRegistry.FARMER,
    	    "careers" // use SRG name "field_189788_c" if using MCP mappings
    	);

    	if (careers != null) {
            for (VillagerCareer career : careers) {
                if ("farmer".equals(career.getName())) {
                    System.out.println("[ChaosMod] Found Farmer career: injecting custom trades.");

                    // Inject BEFORE villagers spawn!
                    career.addTrade(1, new ITradeList() {
                        @Override
                        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
                            System.out.println("Injecting trades for: " + merchant.getDisplayName().getUnformattedText());
                            recipeList.add(new MerchantRecipe(
                                new ItemStack(ModItems.OXONIUM_CARROT, 64),
                                new ItemStack(ModItems.CHAOS_HEART)
                            ));
                        }
                    });
                }
            }
        } else {
            System.out.println("[ChaosMod] Could not retrieve careers from FARMER.");
        }
    }*/
}