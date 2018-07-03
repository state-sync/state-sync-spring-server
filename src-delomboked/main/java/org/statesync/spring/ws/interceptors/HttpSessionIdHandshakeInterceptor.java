package org.statesync.spring.ws.interceptors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(HttpSessionIdHandshakeInterceptor.class);

	@Override
	public void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response,
			final WebSocketHandler wsHandler, final Exception ex) {
	}

	@Override
	public boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response,
			final WebSocketHandler wsHandler, final Map<String, Object> attributes) throws Exception {
		logger.info("Handshake interceptor called!!");
		return true;
	}
}
