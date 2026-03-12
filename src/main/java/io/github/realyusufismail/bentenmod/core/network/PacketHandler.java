/*
 * Copyright 2026 RealYusufIsmail.
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
package io.github.realyusufismail.bentenmod.core.network;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(BenTenMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private static int nextId = 0;

    public static void init() {
        CHANNEL.registerMessage(
                nextId++,
                CTransformPacket.class,
                CTransformPacket::encode,
                CTransformPacket::decode,
                CTransformPacket::handle);
        CHANNEL.registerMessage(
                nextId++, CRevertPacket.class, CRevertPacket::encode, CRevertPacket::decode, CRevertPacket::handle);
        CHANNEL.registerMessage(
                nextId++,
                SSyncOmnitrixPacket.class,
                SSyncOmnitrixPacket::encode,
                SSyncOmnitrixPacket::decode,
                SSyncOmnitrixPacket::handle);
    }
}
