/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.core.omnitrix.ability;

import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.DiamondheadAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.FourArmsAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.GhostfreakAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.GreyMatterAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.HeatblastAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.RipjawsAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.StinkflyAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.UpgradeAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.WildmuttAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens.XLR8Ability;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.Nullable;

public class AlienAbilityRegistry {

    private static final Map<AlienType, AlienAbility> REGISTRY = new EnumMap<>(AlienType.class);

    static {
        register(AlienType.HEATBLAST, new HeatblastAbility());
        register(AlienType.XLR8, new XLR8Ability());
        register(AlienType.FOUR_ARMS, new FourArmsAbility());
        register(AlienType.DIAMONDHEAD, new DiamondheadAbility());
        register(AlienType.GREY_MATTER, new GreyMatterAbility());
        register(AlienType.STINKFLY, new StinkflyAbility());
        register(AlienType.RIPJAWS, new RipjawsAbility());
        register(AlienType.GHOSTFREAK, new GhostfreakAbility());
        register(AlienType.WILDMUTT, new WildmuttAbility());
        register(AlienType.UPGRADE, new UpgradeAbility());
    }

    public static void register(AlienType type, AlienAbility ability) {
        REGISTRY.put(type, ability);
    }

    @Nullable
    public static AlienAbility get(AlienType type) {
        return REGISTRY.get(type);
    }
}
