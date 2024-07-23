package team.durt.elysium.core.platform;

import net.fabricmc.api.EnvType;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.core.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

@ApiStatus.Internal
public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT);
    }
}
