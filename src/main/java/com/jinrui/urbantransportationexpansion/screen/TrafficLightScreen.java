package com.jinrui.urbantransportationexpansion.screen;

import com.jinrui.urbantransportationexpansion.network.ClientNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TrafficLightScreen extends HandledScreen<TrafficLightScreenHandler> {
    private int greenSeconds;
    private int yellowSeconds;
    private int redSeconds;

    public TrafficLightScreen(TrafficLightScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
        this.playerInventoryTitleX = 10000;
        this.playerInventoryTitleY = 10000;
        this.titleX = 10000;
        this.titleY = 10000;
    }

    @Override
    protected void init() {
        super.init();
        this.greenSeconds = this.handler.getGreenTicks() / 20;
        this.yellowSeconds = this.handler.getYellowTicks() / 20;
        this.redSeconds = this.handler.getRedTicks() / 20;

        int left = this.x + 8;
        int top = this.y + 22;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("-1"), b -> this.greenSeconds = clampSeconds(this.greenSeconds - 1)).dimensions(left + 90, top, 24, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+1"), b -> this.greenSeconds = clampSeconds(this.greenSeconds + 1)).dimensions(left + 116, top, 24, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("-1"), b -> this.yellowSeconds = clampSeconds(this.yellowSeconds - 1)).dimensions(left + 90, top + 24, 24, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+1"), b -> this.yellowSeconds = clampSeconds(this.yellowSeconds + 1)).dimensions(left + 116, top + 24, 24, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("-1"), b -> this.redSeconds = clampSeconds(this.redSeconds - 1)).dimensions(left + 90, top + 48, 24, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+1"), b -> this.redSeconds = clampSeconds(this.redSeconds + 1)).dimensions(left + 116, top + 48, 24, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.urbantransportationexpansion.traffic_light.apply"), b ->
                ClientNetworking.sendTrafficLightUpdate(this.handler.getBlockPos(), this.greenSeconds, this.yellowSeconds, this.redSeconds)
        ).dimensions(left, top + 78, 132, 20).build());
    }

    private int clampSeconds(int value) {
        return Math.max(1, Math.min(600, value));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int left = this.x + 8;
        int top = this.y + 6;
        context.fill(this.x, this.y, this.x + this.backgroundWidth, this.y + this.backgroundHeight, 0xCC202020);
        context.drawBorder(this.x, this.y, this.backgroundWidth, this.backgroundHeight, 0xFF5A5A5A);

        context.drawText(this.textRenderer, Text.translatable("gui.urbantransportationexpansion.traffic_light.title"), left, top, 0xFFFFFF, false);

        String phase = switch (this.handler.getPhaseIndex()) {
            case 0 -> "green";
            case 1 -> "yellow";
            case 2 -> "red";
            default -> "unknown";
        };
        int remainingSeconds = Math.max(0, this.handler.getRemainingTicks() / 20);
        context.drawText(this.textRenderer, Text.translatable("gui.urbantransportationexpansion.traffic_light.current", Text.translatable("gui.urbantransportationexpansion.traffic_light.phase." + phase).formatted(Formatting.BOLD), remainingSeconds), left, top + 12, 0xE0E0E0, false);
        context.drawText(this.textRenderer, Text.translatable("gui.urbantransportationexpansion.traffic_light.green"), left, top + 28, 0xA0FFA0, false);
        context.drawText(this.textRenderer, Text.literal(this.greenSeconds + "s"), left + 62, top + 28, 0xFFFFFF, false);
        context.drawText(this.textRenderer, Text.translatable("gui.urbantransportationexpansion.traffic_light.yellow"), left, top + 52, 0xFFF090, false);
        context.drawText(this.textRenderer, Text.literal(this.yellowSeconds + "s"), left + 62, top + 52, 0xFFFFFF, false);
        context.drawText(this.textRenderer, Text.translatable("gui.urbantransportationexpansion.traffic_light.red"), left, top + 76, 0xFFB0B0, false);
        context.drawText(this.textRenderer, Text.literal(this.redSeconds + "s"), left + 62, top + 76, 0xFFFFFF, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
