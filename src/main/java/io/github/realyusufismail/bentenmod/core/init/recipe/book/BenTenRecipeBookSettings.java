package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;

public final class BenTenRecipeBookSettings {
    private static final Map<BenTenRecipeBookType, Pair<String, String>> TAG_FIELDS =
            new HashMap<>(ImmutableMap.of(BenTenRecipeBookType.OMNITRIX_CRAFTING,
                    Pair.of("isGuiOpen", "isFilteringOmnitrixCraftable")));
    private final Map<BenTenRecipeBookType, BenTenRecipeBookSettings.TypeSettings> states;

    private BenTenRecipeBookSettings(
            Map<BenTenRecipeBookType, BenTenRecipeBookSettings.TypeSettings> pStates) {
        this.states = pStates;
    }

    public BenTenRecipeBookSettings() {
        this(Util.make(Maps.newEnumMap(BenTenRecipeBookType.class), (p_12740_) -> {
            for (BenTenRecipeBookType recipebooktype : BenTenRecipeBookType.values()) {
                p_12740_.put(recipebooktype,
                        new BenTenRecipeBookSettings.TypeSettings(false, false));
            }

        }));
    }

    public boolean isOpen(BenTenRecipeBookType pBookType) {
        return (this.states.get(pBookType)).open;
    }

    public void setOpen(BenTenRecipeBookType pBookType, boolean pOpen) {
        (this.states.get(pBookType)).open = pOpen;
    }

    public boolean isFiltering(BenTenRecipeBookType pBookType) {
        return (this.states.get(pBookType)).filtering;
    }

    public void setFiltering(BenTenRecipeBookType pBookType, boolean pFiltering) {
        (this.states.get(pBookType)).filtering = pFiltering;
    }

    public static BenTenRecipeBookSettings read(FriendlyByteBuf pBuffer) {
        Map<BenTenRecipeBookType, BenTenRecipeBookSettings.TypeSettings> map =
                Maps.newEnumMap(BenTenRecipeBookType.class);

        for (BenTenRecipeBookType recipebooktype : BenTenRecipeBookType.values()) {
            boolean flag = pBuffer.readBoolean();
            boolean flag1 = pBuffer.readBoolean();
            map.put(recipebooktype, new BenTenRecipeBookSettings.TypeSettings(flag, flag1));
        }

        return new BenTenRecipeBookSettings(map);
    }

    public void write(FriendlyByteBuf pBuffer) {
        for (BenTenRecipeBookType recipebooktype : BenTenRecipeBookType.values()) {
            BenTenRecipeBookSettings.TypeSettings BenTenrecipebooksettings$typesettings =
                    this.states.get(recipebooktype);
            if (BenTenrecipebooksettings$typesettings == null) {
                pBuffer.writeBoolean(false);
                pBuffer.writeBoolean(false);
            } else {
                pBuffer.writeBoolean(BenTenrecipebooksettings$typesettings.open);
                pBuffer.writeBoolean(BenTenrecipebooksettings$typesettings.filtering);
            }
        }

    }

    public static BenTenRecipeBookSettings read(CompoundTag pTag) {
        Map<BenTenRecipeBookType, BenTenRecipeBookSettings.TypeSettings> map =
                Maps.newEnumMap(BenTenRecipeBookType.class);
        TAG_FIELDS.forEach((p_12750_, p_12751_) -> {
            boolean flag = pTag.getBoolean(p_12751_.getFirst());
            boolean flag1 = pTag.getBoolean(p_12751_.getSecond());
            map.put(p_12750_, new BenTenRecipeBookSettings.TypeSettings(flag, flag1));
        });
        return new BenTenRecipeBookSettings(map);
    }

    public void write(CompoundTag pTag) {
        TAG_FIELDS.forEach((p_12745_, p_12746_) -> {
            BenTenRecipeBookSettings.TypeSettings BenTenrecipebooksettings$typesettings =
                    this.states.get(p_12745_);
            pTag.putBoolean(p_12746_.getFirst(), BenTenrecipebooksettings$typesettings.open);
            pTag.putBoolean(p_12746_.getSecond(), BenTenrecipebooksettings$typesettings.filtering);
        });
    }

    public BenTenRecipeBookSettings copy() {
        Map<BenTenRecipeBookType, BenTenRecipeBookSettings.TypeSettings> map =
                Maps.newEnumMap(BenTenRecipeBookType.class);

        for (BenTenRecipeBookType recipebooktype : BenTenRecipeBookType.values()) {
            BenTenRecipeBookSettings.TypeSettings BenTenrecipebooksettings$typesettings =
                    this.states.get(recipebooktype);
            map.put(recipebooktype, BenTenrecipebooksettings$typesettings.copy());
        }

        return new BenTenRecipeBookSettings(map);
    }

    public void replaceFrom(BenTenRecipeBookSettings pOther) {
        this.states.clear();

        for (BenTenRecipeBookType recipebooktype : BenTenRecipeBookType.values()) {
            BenTenRecipeBookSettings.TypeSettings BenTenrecipebooksettings$typesettings =
                    pOther.states.get(recipebooktype);
            this.states.put(recipebooktype, BenTenrecipebooksettings$typesettings.copy());
        }

    }

    public boolean equals(Object pOther) {
        return this == pOther || pOther instanceof BenTenRecipeBookSettings
                && this.states.equals(((BenTenRecipeBookSettings) pOther).states);
    }

    public int hashCode() {
        return this.states.hashCode();
    }

    static final class TypeSettings {
        boolean open;
        boolean filtering;

        public TypeSettings(boolean pOpen, boolean pFiltering) {
            this.open = pOpen;
            this.filtering = pFiltering;
        }

        public BenTenRecipeBookSettings.TypeSettings copy() {
            return new BenTenRecipeBookSettings.TypeSettings(this.open, this.filtering);
        }

        public boolean equals(Object pOther) {
            if (this == pOther) {
                return true;
            } else if (!(pOther instanceof BenTenRecipeBookSettings.TypeSettings)) {
                return false;
            } else {
                BenTenRecipeBookSettings.TypeSettings BenTenrecipebooksettings$typesettings =
                        (BenTenRecipeBookSettings.TypeSettings) pOther;
                return this.open == BenTenrecipebooksettings$typesettings.open
                        && this.filtering == BenTenrecipebooksettings$typesettings.filtering;
            }
        }

        public int hashCode() {
            int i = this.open ? 1 : 0;
            return 31 * i + (this.filtering ? 1 : 0);
        }

        public String toString() {
            return "[open=" + this.open + ", filtering=" + this.filtering + "]";
        }
    }

    // FORGE -- called automatically on Enum creation - used for serialization
    public static void addTagsForType(BenTenRecipeBookType type, String openTag,
            String filteringTag) {
        TAG_FIELDS.put(type, Pair.of(openTag, filteringTag));
    }
}
