package team.durt.elysium.core.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import team.durt.elysium.core.Constants;
import team.durt.elysium.core.network.AnimationControllerSyncHandler;
import team.durt.elysium.core.network.AnimationControllerSyncPayload;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ElysiumNeoForgeBusEvents {
    @SubscribeEvent
    public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(AnimationControllerSyncPayload.TYPE, AnimationControllerSyncPayload.STREAM_CODEC, AnimationControllerSyncHandler::handle);
    }
}