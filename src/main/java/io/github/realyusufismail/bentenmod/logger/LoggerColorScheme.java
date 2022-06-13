package io.github.realyusufismail.bentenmod.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to colorize the log output.
 * <p>
 * The debug output is colored blue. <br>
 * The info output is colored green. <br>
 * The warn output is colored yellow. <br>
 * The error output is colored red. <br>
 * The fatal output is colored magenta. <br>
 * The trace output is colored cyan. <br>
 * The default output is colored the default logging colour.
 * </p>
 */
public class LoggerColorScheme extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(@NotNull ILoggingEvent event) {
        return switch (event.getLevel().toInt()) {
            case Level.DEBUG_INT -> ANSIConstants.BLUE_FG;
            case Level.INFO_INT -> ANSIConstants.GREEN_FG;
            case Level.WARN_INT -> ANSIConstants.YELLOW_FG;
            case Level.ERROR_INT -> ANSIConstants.RED_FG;
            case Level.TRACE_INT -> ANSIConstants.CYAN_FG;
            default -> ANSIConstants.DEFAULT_FG;
        };
    }
}
