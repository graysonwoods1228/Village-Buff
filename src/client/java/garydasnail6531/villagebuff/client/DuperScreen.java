package garydasnail6531.villagebuff.client;

import garydasnail6531.villagebuff.Duper.DuperMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DuperScreen extends AbstractContainerScreen<DuperMenu> {
    private Button autoDupeButton;

    public DuperScreen(DuperMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;

        autoDupeButton = Button.builder(autoDupeLabel(), button -> {
                    if (minecraft != null && minecraft.gameMode != null) {
                        minecraft.gameMode.handleInventoryButtonClick(menu.containerId, DuperMenu.AUTO_DUPE_BUTTON);
                    }
                })
                .bounds(leftPos + 113, topPos + 37, 52, 20)
                .build();
        addRenderableWidget(autoDupeButton);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (autoDupeButton != null) {
            autoDupeButton.setMessage(autoDupeLabel());
        }

        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;

        graphics.fill(x, y, x + imageWidth, y + imageHeight, 0xffc6c6c6);
        graphics.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, 0xffffffff);
        graphics.fill(x + 2, y + 2, x + imageWidth - 2, y + imageHeight - 2, 0xff8b8b8b);
        graphics.fill(x + 3, y + 3, x + imageWidth - 3, y + imageHeight - 3, 0xffc6c6c6);

        graphics.fill(x + 12, y + 18, x + 166, y + 68, 0xff3f3f3f);
        graphics.fill(x + 14, y + 20, x + 164, y + 66, 0xff1a1a1f);
        graphics.fill(x + 16, y + 22, x + 106, y + 64, 0xff0f0f14);
        graphics.fill(x + 110, y + 22, x + 162, y + 64, menu.isAutoDupeEnabled() ? 0xff2a113f : 0xff171717);

        graphics.drawString(font, "Items", x + 44, y + 31, 0xffe0e0e0, false);
        graphics.drawString(font, "Auto", x + 126, y + 27, 0xffe0e0e0, false);
        graphics.drawString(font, menu.isAutoDupeEnabled() ? "ON" : "OFF", x + 129, y + 57, menu.isAutoDupeEnabled() ? 0xffc77dff : 0xffa0a0a0, false);

        drawSlot(graphics, x + 43, y + 43);
        drawSlot(graphics, x + 79, y + 43);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                drawSlot(graphics, x + 7 + column * 18, y + 83 + row * 18);
            }
        }

        for (int column = 0; column < 9; column++) {
            drawSlot(graphics, x + 7 + column * 18, y + 141);
        }
    }

    private void drawSlot(GuiGraphics graphics, int x, int y) {
        graphics.fill(x, y, x + 18, y + 18, 0xff373737);
        graphics.fill(x + 1, y + 1, x + 18, y + 18, 0xffffffff);
        graphics.fill(x + 1, y + 1, x + 17, y + 17, 0xff8b8b8b);
        graphics.fill(x + 2, y + 2, x + 17, y + 17, 0xff373737);
        graphics.fill(x + 2, y + 2, x + 16, y + 16, 0xff8b8b8b);
    }

    private Component autoDupeLabel() {
        return Component.literal(menu.isAutoDupeEnabled() ? "On" : "Off");
    }
}
