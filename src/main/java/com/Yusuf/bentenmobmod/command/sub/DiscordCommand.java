package com.Yusuf.bentenmobmod.command.sub;

import static com.Yusuf.bentenmobmod.utils.ToolTipUtils.translate;
import static net.minecraft.util.text.TextFormatting.AQUA;
import static net.minecraft.util.text.event.ClickEvent.Action.OPEN_URL;
import static net.minecraft.util.text.event.HoverEvent.Action.SHOW_TEXT;

import com.mojang.brigadier.Command;

import net.minecraft.command.CommandSource;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

/**
 * @author Yusuf Ismail
 */

public class DiscordCommand {

    public static int execute(CommandSource sender) {
        String discord = "https://discord.gg/g858J6q";
        Style wiki = Style.EMPTY.withClickEvent(new ClickEvent(OPEN_URL, discord)).withHoverEvent(new HoverEvent(SHOW_TEXT, translate("commands.betenmobmod.discrod.link_details")));
        sender.sendSuccess(translate(AQUA, "commands.betenmobmod.discord.link_details", discord).setStyle(wiki), false);
        return Command.SINGLE_SUCCESS;
    }
}
