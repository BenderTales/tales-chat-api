package com.bendertales.mc.chatapi.impl.vo;

public record PlayerChannelStatus(
	Channel channel,
	boolean target,
	boolean hidden
) {

}
