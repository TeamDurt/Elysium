package team.durt.elysium.core;

import net.fabricmc.api.ModInitializer;

public class ElysiumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElysiumCommon.init();
    }
}
