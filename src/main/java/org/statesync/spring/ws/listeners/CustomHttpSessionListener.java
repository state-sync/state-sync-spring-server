package org.statesync.spring.ws.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.java.Log;

@Log
public class CustomHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(final HttpSessionEvent httpSessionEvent) {
		log.severe("Session Creation event called!! : " + httpSessionEvent.getSession().getId());
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent httpSessionEvent) {
		log.severe("Session Destroyed event called!! : " + httpSessionEvent.getSession().getId());
	}
}
