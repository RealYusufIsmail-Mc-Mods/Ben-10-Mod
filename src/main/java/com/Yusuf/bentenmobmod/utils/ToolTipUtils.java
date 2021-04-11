package com.Yusuf.bentenmobmod.utils;

import static net.minecraft.util.text.Style.EMPTY;

import com.ibm.icu.text.ListFormatter.Style;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
public class ToolTipUtils {

	 /**
     * This provides the "Press [Key] to show more" tooltip
     *
     * @param tooltip    the tooltip of the item
     * @param keyBinding the keybind that the users will need to press to display the more information (replaces [Key])
     * @param formatting the formatting of the tooltip, its color and style.
     */


    /**
     * Adds a basic damage information about arrows
     */
 

	 public static IFormattableTextComponent translate(net.minecraft.util.text.Color color, String key, Object... args) {
	        return new TranslationTextComponent(key, args).setStyle(EMPTY.withColor(color));
	    }


	    public static IFormattableTextComponent translate(net.minecraft.util.text.Style style, String key, Object... args) {
	        return new TranslationTextComponent(key, args).setStyle(style);
	    }

	    public static IFormattableTextComponent translate(TextFormatting formatting, String key, Object... args) {
	        return new TranslationTextComponent(key, args).withStyle(formatting);
	    }

	    public static TranslationTextComponent translate(String key, Object... args) {
	        return new TranslationTextComponent(key, args);
	    }

}

