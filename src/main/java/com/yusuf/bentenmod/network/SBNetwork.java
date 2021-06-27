package com.yusuf.bentenmod.network;

import com.yusuf.bentenmod.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SBNetwork {
    public ResourceLocation channelName;
    public String networkVersion;

    private SimpleChannel network;

    public SimpleChannel register() {
        channelName = new ResourceLocation(Main.MOD_ID, "network");
        networkVersion = new ResourceLocation(Main.MOD_ID, "1").toString();

        network = NetworkRegistry.ChannelBuilder.named(channelName)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(() -> networkVersion)
                .simpleChannel();

        network.messageBuilder(ToggleMessage.class, 1)
                .decoder(ToggleMessage::decode)
                .encoder(ToggleMessage::encode)
                .consumer(ToggleMessage::handle)
                .add();
        network.messageBuilder(OpenMessage.class, 2)
                .decoder(OpenMessage::decode)
                .encoder(OpenMessage::encode)
                .consumer(OpenMessage::handle)
                .add();
        network.messageBuilder(ToggleMessageMessage.class, 3)
                .decoder(ToggleMessageMessage::decode)
                .encoder(ToggleMessageMessage::encode)
                .consumer(ToggleMessageMessage::handle)
                .add();
        network.messageBuilder(FilterMessage.class, 4)
                .decoder(FilterMessage::decode)
                .encoder(FilterMessage::encode)
                .consumer(FilterMessage::handle)
                .add();

        return network;
    }

    public SimpleChannel getNetworkChannel() {
        return network;
    }
}

