package com.bendertales.mc.chatapi.impl.formats;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.bendertales.mc.chatapi.ChatConstants;
import com.bendertales.mc.chatapi.api.PlaceholderFormatter;
import com.bendertales.mc.chatapi.api.PlaceholderHandler;
import net.minecraft.util.Identifier;


public class DateTimePlaceholderHandler implements PlaceholderHandler {

	private static final String HOUR_PLACEHOLDER = "%HH%";
	private static final String MINUTES_PLACEHOLDER = "%mm%";
	private static final String SECONDS_PLACEHOLDER = "%SS%";

	private final DateTimeFormatter hoursFormatter = DateTimeFormatter.ofPattern("HH");
	private final DateTimeFormatter minutesFormatter = DateTimeFormatter.ofPattern("mm");
	private final DateTimeFormatter secondsFormatter = DateTimeFormatter.ofPattern("SS");

	@Override
	public int getDefaultPriorityOrder() {
		return 1;
	}

	@Override
	public boolean shouldApplyFormat(String format) {
		return format.contains(HOUR_PLACEHOLDER)
		       || format.contains(MINUTES_PLACEHOLDER)
		       || format.contains(SECONDS_PLACEHOLDER);
	}

	@Override
	public PlaceholderFormatter getPlaceholderFormatter() {
		return (format, message) -> {
			var now = LocalTime.now();
			return format.replace(HOUR_PLACEHOLDER, now.format(hoursFormatter))
			             .replace(MINUTES_PLACEHOLDER, now.format(minutesFormatter))
			             .replace(SECONDS_PLACEHOLDER, now.format(secondsFormatter));
		};
	}

	@Override
	public Identifier getId() {
		return ChatConstants.Ids.Formats.TIME;
	}
}
