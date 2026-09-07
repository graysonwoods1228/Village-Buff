package garydasnail6531.villagebuff.client;

import garydasnail6531.villagebuff.Duper.DuperBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class VillageBuffClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(DuperBlocks.DUPER_MENU, DuperScreen::new);
    }
}
