package com.bendertales.mc.chatapi.command;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.chatapi.impl.ChatManager;
import com.bendertales.mc.chatapi.impl.vo.Channel;
import com.bendertales.mc.chatapi.impl.vo.PlayerChannelStatus;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;


public class CmdList implements ModCommand {

	private final ChatManager chatManager;

	public CmdList(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayer();
		if (player != null) {
			return listToPlayer(player);
		}

		return listToServer(cmdSource);
	}

	private int listToServer(ServerCommandSource commandSource) {
		var channels = chatManager.getChannels();

		for (Channel channel : channels) {
			commandSource.sendFeedback(Text.of(channel.id().toString()), false);
		}

		return channels.size();
	}

	private int listToPlayer(ServerPlayerEntity player) {
		var channelsStatus = chatManager.getPlayerChannelsStatus(player);

		for (PlayerChannelStatus status : channelsStatus) {
			var channelName = status.channel().id().toString();
			String firstColor = status.selected() ? "§3" : "§f";
			var hideStatus = status.hidden() ? "§cHidden" : "§aVisible";
			player.sendMessage(Text.of(firstColor + channelName + ": " + hideStatus), false);
		}

		return channelsStatus.size();
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return List.of("chatapi.commands.admin", "chatapi.commands.list");
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal("channel")
				.then(literal("list")
			        .requires(getRequirements())
			        .executes(this)
				)
		);
	}
}
