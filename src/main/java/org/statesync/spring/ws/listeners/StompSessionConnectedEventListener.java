
package org.statesync.spring.ws.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.statesync.spring.SpringSyncService;
import org.statesync.spring.ws.interceptors.HttpSessionIdHandshakeInterceptor;

@Component
public class StompSessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent>
{
	private static final Logger logger = LoggerFactory.getLogger(HttpSessionIdHandshakeInterceptor.class);
	@Autowired
	private SpringSyncService syncService;

	@Override
	public void onApplicationEvent(final SessionConnectedEvent event)
	{
		try
		{
			logger.error("StompSubscribeEventListener.#onApplicationEvent.SessionSubscribeEvent" + event.toString());
			this.syncService.onSessionConnected(event);
			final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
			logger.error(headerAccessor.toString());
		}
		catch (Exception e)
		{
			logger.error("fail:", e);
			throw new RuntimeException(e);
		}
	}
}
