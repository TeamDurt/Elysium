package team.durt.elysium.core;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.animation.definitions.CamelAnimation;
import net.minecraft.world.entity.animal.camel.Camel;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.group.ElysiumAnimationGroup;
import team.durt.elysium.core.platform.Services;
import team.durt.elysium.impl.animation.controller.ElysiumAnimationControllerImpl;
import team.durt.elysium.impl.animation.group.ElysiumAnimationGroupImpl;

public class ElysiumCommon {
    public static void init() {
        ElysiumAnimationController<Camel> camelAnimationController = new ElysiumAnimationControllerImpl.Builder<Camel>(null, ElysiumAnimationController.ComputedBy.SERVER)
                .walking(CamelAnimation.CAMEL_WALK, 2.0F, 2.5F, camel -> true)
                .addGroup(
                        "idle",
                        new ElysiumAnimationGroupImpl.Builder<Camel>()
                                .define("idle", CamelAnimation.CAMEL_IDLE)
                                .addState("none", ImmutableList.of(), ImmutableList.of(
                                        new ElysiumAnimationGroup.Transition<>("idle", camel -> !camel.hasImpulse, ImmutableList.of())
                                ))
                                .addState("idle", ImmutableList.of("idle"), ImmutableList.of(
                                        new ElysiumAnimationGroup.Transition<>("none", camel -> camel.hasImpulse, ImmutableList.of())
                                ))
                                .defaultState("idle")
                                .build()
                )
                .build();

        if (Services.PLATFORM.isModLoaded(Constants.MOD_ID)) {
            Constants.LOG.info("Meet Elysium! May you two hit it off famously");
        }
    }
}