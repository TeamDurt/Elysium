package team.durt.elysium.core;

import team.durt.elysium.core.platform.Services;

public class ElysiumCommon {
    public static void init() {
        if (Services.PLATFORM.isModLoaded(Constants.MOD_ID)) {
            Constants.LOG.info("Meet Elysium! May you two hit it off famously");
        }
    }
}