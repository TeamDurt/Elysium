package team.durt.elysium.core;

import net.fabricmc.api.ModInitializer;
import team.durt.elysium.core.registry.ElysiumMessages;

public class ElysiumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElysiumCommon.init();

        ElysiumMessages.registerPayloadTypes();

        ElysiumMessages.registerC2SPackets();
    }
}
