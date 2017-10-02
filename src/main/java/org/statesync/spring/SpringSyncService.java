package org.statesync.spring;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import org.statesync.SyncService;
import org.statesync.info.StateSyncInfo;
import org.statesync.protocol.RequestMessage;
import org.statesync.protocol.init.InitSessionResponse;

@Service
public class SpringSyncService {
	private SyncService core;
	private SpringProtocol protocol;

	@Autowired
	public SpringSyncService(final SimpMessagingTemplate simpMessagingTemplate) {
		this.protocol = new SpringProtocol(simpMessagingTemplate);
		this.core = new SyncService(this.protocol);
	}

	public InitSessionResponse connect(final Principal principal, final String externalSessionId) {
		return this.core.connect(principal == null ? "@anonymous" : principal.getName(), externalSessionId);
	}

	public StateSyncInfo getInfo() {
		return this.core.getInfo();
	}

	public void handle(final String sessionToken, final RequestMessage request) {
		this.core.handle(sessionToken, request);
	}

	public void onSessionConnected(final SessionConnectedEvent event) {
		// TODO Auto-generated method stub

	}

	public void onSessionDisconnect(final SessionDisconnectEvent event) {
		final String externalSessionId = event.getSessionId();
		this.core.disconnectSession(externalSessionId);
	}

	public void onSubscribe(final SessionSubscribeEvent event) {
		// TODO Auto-generated method stub

	}

	public void onUnsubscribe(final SessionUnsubscribeEvent event) {
		// TODO Auto-generated method stub

	}

	public void register(final SpringSyncArea<?> springSyncArea) {
		this.core.register(springSyncArea.getArea());
	}
}
