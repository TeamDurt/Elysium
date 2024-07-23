package team.durt.elysium.core;

import net.fabricmc.api.ClientModInitializer;
import team.durt.elysium.core.registry.ElysiumMessages;

public class ElysiumFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ElysiumMessages.registerS2CPackets();
    }
}
