package team.durt.elysium.api.animation.group;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.group.state.ElysiumAnimationGroupState;

import java.util.List;

public interface StateAnimationGroup<T extends LivingEntity> {
    List<ElysiumAnimationGroupState<T>> getStates();

    String defaultState();

    ElysiumAnimationGroupState<T> activeState();

    void setActiveState(String stateName);
}
