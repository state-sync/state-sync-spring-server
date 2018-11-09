package org.statesync.spring;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class StateSyncWebSocketController
{

	private static final Logger log = Logger.getLogger(StateSyncWebSocketController.class.getName());

	private RequestMessageFactory requestFactory = new RequestMessageFactory();

	@Autowired
	private SpringSyncService syncService;

	@Autowired
	public StateSyncWebSocketController(final SpringSyncService syncService)
	{
		this.syncService = syncService;
	}

	/**
	 * Handle initial connect, response with configuration and
	 *
	 * @param accessor
	 * @return session initialization command
	 */
	@MessageMapping("/init/{sessionToken}")
	public InitSessionResponse connect(final @DestinationVariable String sessionToken,
			final SimpMessageHeaderAccessor accessor)
	{
		final Principal principal = accessor.getUser();
		final String externalSessionId = accessor.getSessionId();
		return this.syncService.connect(principal, externalSessionId, sessionToken);
	}

	@MessageMapping("/request/{sessionToken}")
	public void request(final @DestinationVariable String sessionToken, final JsonNode cmd,
			final SimpMessageHeaderAccessor accessor)
	{
		try
		{
			final RequestMessage request = this.requestFactory.parse(cmd);
			this.syncService.handle(sessionToken, request);
		}
		catch (final Exception e)
		{
			log.log(Level.SEVERE, "init failed", e);
			throw e;
		}
	}

	@SubscribeMapping("/session/{sessionToken}")
	public void sessionSubscribe(final @DestinationVariable String sessionToken,
			final SimpMessageHeaderAccessor accessor)
	{
		log.fine("Trace: webSocket session subscribe " + sessionToken);
	}
}
