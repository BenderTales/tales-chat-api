package com.bendertales.mc.chatapi.command.shortcuts;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.chatapi.ChatConstants;
import com.bendertales.mc.chatapi.api.MessageSender;
import com.bendertales.mc.chatapi.impl.channels.StaffChannel;
import net.minecraft.util.Identifier;


public class CmdChatStaff extends ShortcutModCommand{

	public CmdChatStaff(MessageSender messageSender) {
		super(messageSender);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return List.of(StaffChannel.PERMISSION);
	}

	@Override
	protected String getCommandRoot() {
		return "cstf";
	}

	@Override
	protected Identifier getChannelId() {
		return ChatConstants.Ids.Channels.STAFF;
	}
}
