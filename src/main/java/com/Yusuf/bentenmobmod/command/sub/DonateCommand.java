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
public class DonateCommand {
	  public static int execute(CommandSource sender) {
	        String link = "https://gofund.me/abdcb233";
	        Style wiki = Style.EMPTY.withClickEvent(new ClickEvent(OPEN_URL, link)).withHoverEvent(new HoverEvent(SHOW_TEXT, translate("commands.betenmobmod.donate.link_details")));
	        sender.sendSuccess(translate(AQUA, "commands.betenmobmod.donate.link_details", link).setStyle(wiki), false);
	        return Command.SINGLE_SUCCESS;
	    }
}
