package team.durt.elysium.core.platform;

import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.core.Constants;
import team.durt.elysium.core.platform.services.INetworkHelper;
import team.durt.elysium.core.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

@ApiStatus.Internal
public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final INetworkHelper NETWORK = load(INetworkHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}