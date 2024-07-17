package team.durt.elysium.core;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ElysiumNeoForge {
    public ElysiumNeoForge(IEventBus eventBus) {
        ElysiumCommon.init();
    }
}