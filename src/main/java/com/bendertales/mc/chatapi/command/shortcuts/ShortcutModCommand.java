package com.bendertales.mc.chatapi.command.shortcuts;

import com.bendertales.mc.chatapi.api.ChatException;
import com.bendertales.mc.chatapi.api.Messenger;
import com.bendertales.mc.chatapi.command.ModCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public abstract class ShortcutModCommand implements ModCommand {

	private final Messenger messenger;

	protected ShortcutModCommand(Messenger messenger) {
		this.messenger = messenger;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayer();
		var message = context.getArgument("message", String.class);

		try {
			messenger.sendMessage(player, message, getChannelId());
		}
		catch (ChatException e) {
			var msg = Text.literal(e.getMessage()).formatted(Formatting.RED);
			throw new CommandSyntaxException(new SimpleCommandExceptionType(msg), msg);
		}
		return SINGLE_SUCCESS;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal(getCommandRoot())
				.requires(getRequirements())
				.then(argument("message", StringArgumentType.greedyString())
			        .executes(this))
		);
	}

	protected abstract String getCommandRoot();
	protected abstract Identifier getChannelId();
}
