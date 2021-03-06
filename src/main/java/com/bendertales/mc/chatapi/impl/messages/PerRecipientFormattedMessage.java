package com.bendertales.mc.chatapi.impl.messages;

import java.util.List;

import com.bendertales.mc.chatapi.api.Message;
import com.bendertales.mc.chatapi.api.SpecificToRecipientPlaceholderFormatter;
import com.bendertales.mc.chatapi.impl.vo.MessageOptions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class PerRecipientFormattedMessage implements FormattedMessage {

	private final Message                                       message;
	private final List<SpecificToRecipientPlaceholderFormatter> recipientPlaceholderFormatters;
	private final String                                        line;

	public PerRecipientFormattedMessage(Message message,
	                                    List<SpecificToRecipientPlaceholderFormatter> recipientPlaceholderFormatters,
	                                    String line) {
		this.message = message;
		this.recipientPlaceholderFormatters = recipientPlaceholderFormatters;
		this.line = line;
	}


	@Override
	public Text forRecipient(ServerPlayerEntity recipient, MessageOptions options) {
		String line = this.line;

		for (SpecificToRecipientPlaceholderFormatter formatter : recipientPlaceholderFormatters) {
			line = formatter.formatMessage(line, message, recipient);
		}

		if (options.socialSpy()) {
			line = "§m*§r" + line;
		}

		return Text.of(line);
	}
}
