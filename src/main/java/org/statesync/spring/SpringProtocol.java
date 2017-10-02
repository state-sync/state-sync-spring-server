package org.statesync.spring;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.statesync.SyncOutbound;
import org.statesync.protocol.EventMessage;
import org.statesync.protocol.ResponseMessage;

public class SpringProtocol implements SyncOutbound {

	private SimpMessagingTemplate simpMessagingTemplate;

	public SpringProtocol(final SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@Override
	public void broadcast(final String userToken, final EventMessage event) {
		this.simpMessagingTemplate.convertAndSend("/account/" + userToken, event);
	}

	@Override
	public void send(final String sessionToken, final ResponseMessage event) {
		this.simpMessagingTemplate.convertAndSend("/session/" + sessionToken, event);
	}

}
