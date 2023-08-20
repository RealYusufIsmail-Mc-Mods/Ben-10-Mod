package io.github.realyusufismail.bentenmod.omnitrix;

import lombok.Getter;

@Getter
public enum Ben10Aliens {
    HEATBLAST("Heatblast"),
    FOURARMS("Four Arms"),
    XLR8("XLR8"),
    DIAMONDHEAD("Diamondhead"),
    WILDMUTT("Wildmutt"),
    STINKFLY("Stinkfly"),
    UPGRADE("Upgrade"),
    GREYMATTER("Grey Matter"),
    RIPJAWS("Ripjaws"),
    CANNONBOLT("Cannonbolt"),
    WAYBIG("Way Big");

    private final String displayName;

    Ben10Aliens(String displayName) {
        this.displayName = displayName;
    }
}
