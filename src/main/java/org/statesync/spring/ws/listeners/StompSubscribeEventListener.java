
package org.statesync.spring.ws.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.statesync.spring.SpringSyncService;
import org.statesync.spring.ws.interceptors.HttpSessionIdHandshakeInterceptor;

@Component
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent>
{
	private static final Logger logger = LoggerFactory.getLogger(HttpSessionIdHandshakeInterceptor.class);
	@Autowired
	private SpringSyncService syncService;

	@Override
	public void onApplicationEvent(final SessionSubscribeEvent event)
	{
		try
		{
			logger.info("StompSubscribeEventListener.#onApplicationEvent.SessionSubscribeEvent" + event.toString());
			this.syncService.onSubscribe(event);
			final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
			logger.info(headerAccessor.toString());
		}
		catch (Exception e)
		{
			logger.error("fail:", e);
			throw new RuntimeException(e);
		}
	}
}
