package com.bendertales.mc.chatapi.impl.formats;

import com.bendertales.mc.chatapi.ChatConstants;
import com.bendertales.mc.chatapi.api.PlaceholderFormatter;
import com.bendertales.mc.chatapi.api.PlaceholderHandler;
import net.minecraft.util.Identifier;


public class MessagePlaceholderHandler implements PlaceholderHandler {

	private static final String MESSAGE_PLACEHOLDER = "%MESSAGE%";

	@Override
	public int getDefaultPriorityOrder() {
		return 99;
	}

	@Override
	public boolean shouldApplyFormat(String format) {
		return format.contains(MESSAGE_PLACEHOLDER);
	}

	@Override
	public PlaceholderFormatter getPlaceholderFormatter() {
		return (format,  message) -> format.replace(MESSAGE_PLACEHOLDER, message.content());
	}

	@Override
	public Identifier getId() {
		return ChatConstants.Ids.Formats.MESSAGE;
	}
}
