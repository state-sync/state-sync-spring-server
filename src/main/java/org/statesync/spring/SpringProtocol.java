package org.statesync.spring;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.statesync.SyncOutbound;
import org.statesync.protocol.Message;

public class SpringProtocol implements SyncOutbound {

	private SimpMessagingTemplate simpMessagingTemplate;

	public SpringProtocol(final SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@Override
	public void send(final String sessionToken, final Message event) {
		this.simpMessagingTemplate.convertAndSend("/session/" + sessionToken, event);
	}

}
