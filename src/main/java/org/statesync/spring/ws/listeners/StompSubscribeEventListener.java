package org.statesync.spring.ws.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.statesync.spring.SpringSyncService;

import lombok.extern.java.Log;

@Component
@Log
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

	@Autowired
	private SpringSyncService syncService;

	@Override
	public void onApplicationEvent(final SessionSubscribeEvent event) {
		this.syncService.onSubscribe(event);
		final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		log.severe(headerAccessor.toString());
	}
}
