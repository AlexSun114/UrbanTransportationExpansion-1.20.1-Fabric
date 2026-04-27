package com.jinrui.urbantransportationexpansion.screen;

import com.jinrui.urbantransportationexpansion.UrbanTransportationExpansion;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<TrafficLightScreenHandler> TRAFFIC_LIGHT = Registry.register(
            Registries.SCREEN_HANDLER,
            new Identifier(UrbanTransportationExpansion.MOD_ID, "traffic_light"),
            new ExtendedScreenHandlerType<>(TrafficLightScreenHandler::new)
    );

    public static void register() {
    }
}
