package org.statesync.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.statesync.SyncOutbound;
import org.statesync.protocol.EventMessage;
import org.statesync.protocol.ResponseMessage;

@Service
public class SpringProtocol implements SyncOutbound {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	public void broadcast(final String userToken, final EventMessage event) {

	}

	public void send(final String sessionId, final ResponseMessage event) {

	}

}
