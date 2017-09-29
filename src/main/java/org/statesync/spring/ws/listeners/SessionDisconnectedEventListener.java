package org.statesync.spring.ws.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.statesync.spring.SpringSyncService;

import lombok.extern.java.Log;

@Component
@Log
public class SessionDisconnectedEventListener implements ApplicationListener<SessionDisconnectEvent> {

	@Autowired
	private SpringSyncService syncService;

	@Override
	public void onApplicationEvent(final SessionDisconnectEvent event) {
		this.syncService.onSessionDisconnect(event);
		final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		log.severe(headerAccessor.toString());
	}
}
