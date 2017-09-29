package org.statesync.spring;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.statesync.protocol.RequestMessage;
import org.statesync.protocol.RequestMessageFactory;
import org.statesync.protocol.init.InitSessionResponse;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class StateSyncWebSocketController {

	private RequestMessageFactory requestFactory = new RequestMessageFactory();
	@Autowired
	private SpringSyncService syncService;

	@Autowired
	public StateSyncWebSocketController(final SpringSyncService syncService) {
		this.syncService = syncService;
	}

	/**
	 * Handle initial connect, response with configuration and
	 *
	 * @param accessor
	 * @return session initialization command
	 */
	@SubscribeMapping("/root")
	public InitSessionResponse connect(final SimpMessageHeaderAccessor accessor) {
		final Principal principal = accessor.getUser();
		final String externalSessionId = accessor.getSessionId();
		return this.syncService.connect(principal, externalSessionId);
	}

	@MessageMapping("/session/{sessionToken}")
	public void sessionRequest(final @DestinationVariable String sessionToken, final JsonNode cmd,
			final SimpMessageHeaderAccessor accessor) {
		final RequestMessage request = this.requestFactory.parse(cmd);
		this.syncService.handle(sessionToken, request);
	}

	@MessageMapping("/user/{userToken}")
	public void userSubscription(final @DestinationVariable String userToken, final JsonNode cmd,
			final SimpMessageHeaderAccessor accessor) {
		// do nothing, Stomp client subscribed to user token
	}
}
