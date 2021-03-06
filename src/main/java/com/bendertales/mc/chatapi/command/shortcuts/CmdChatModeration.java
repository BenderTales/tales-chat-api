package com.bendertales.mc.chatapi.command.shortcuts;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.chatapi.ChatConstants;
import com.bendertales.mc.chatapi.api.Messenger;
import com.bendertales.mc.chatapi.impl.channels.ModerationChannel;
import net.minecraft.util.Identifier;


public class CmdChatModeration extends ShortcutModCommand{

	public CmdChatModeration(Messenger messenger) {
		super(messenger);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return List.of(ModerationChannel.PERMISSION);
	}

	@Override
	protected String getCommandRoot() {
		return "cmod";
	}

	@Override
	protected Identifier getChannelId() {
		return ChatConstants.Ids.Channels.MODO;
	}
}
