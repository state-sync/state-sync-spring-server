
package org.statesync.spring.ws.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CustomHttpSessionListener implements HttpSessionListener
{
	@java.lang.SuppressWarnings("all")
	private static final java.util.logging.Logger log = java.util.logging.Logger
			.getLogger(CustomHttpSessionListener.class.getName());

	@Override
	public void sessionCreated(final HttpSessionEvent httpSessionEvent)
	{
		log.severe("Session Creation event called!! : " + httpSessionEvent.getSession().getId());
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent httpSessionEvent)
	{
		log.severe("Session Destroyed event called!! : " + httpSessionEvent.getSession().getId());
	}
}
