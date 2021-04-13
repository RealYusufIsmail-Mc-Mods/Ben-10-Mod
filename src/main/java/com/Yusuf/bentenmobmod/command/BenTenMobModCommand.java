package com.Yusuf.bentenmobmod.command;

import com.Yusuf.bentenmobmod.command.sub.DiscordCommand;
import com.Yusuf.bentenmobmod.command.sub.DonateCommand;
import com.Yusuf.bentenmobmod.command.sub.SecreteCommand;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class BenTenMobModCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("bentenmobmod").requires(cs -> cs.hasPermission(0))
                .then(Commands.literal("discord").executes(ctx -> discord(ctx.getSource())))
                
                .then(Commands.literal("donate").executes(ctx -> donate(ctx.getSource())))
                .then(Commands.literal("secrete").executes(ctx -> secrete(ctx.getSource())))
                
        );
    }
	
	private static int discord(CommandSource source) {
        return DiscordCommand.execute(source);
    }

    private static int donate(CommandSource source) {
        return DonateCommand.execute(source);
    }
    

    private static int secrete(CommandSource source) {
        return SecreteCommand.execute(source);
    }

}
