package com.jinrui.urbantransportationexpansion;

import com.jinrui.urbantransportationexpansion.screen.ModScreenHandlers;
import com.jinrui.urbantransportationexpansion.screen.TrafficLightScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class UrbanTransportationExpansionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.TRAFFIC_LIGHT, TrafficLightScreen::new);
    }
}
