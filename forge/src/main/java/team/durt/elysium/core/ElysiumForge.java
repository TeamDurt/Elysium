package team.durt.elysium.core;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.durt.elysium.core.registry.ElysiumMessages;

@Mod(Constants.MOD_ID)
public class ElysiumForge {
    public ElysiumForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ElysiumCommon.init();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ElysiumMessages.register();
    }
}