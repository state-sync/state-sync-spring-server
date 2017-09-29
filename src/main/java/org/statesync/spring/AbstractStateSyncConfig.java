package org.statesync.spring;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.statesync.spring.ws.interceptors.HttpSessionIdHandshakeInterceptor;

public class AbstractStateSyncConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(final MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/connect", "/session", "/user");
	}

	protected String getAllowedOrigins() {
		return "*";
	}

	protected String getEndpointPath() {
		return "/state-sync";
	}

	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		registry.addEndpoint(getEndpointPath()).setAllowedOrigins(getAllowedOrigins())
				.addInterceptors(new HttpSessionIdHandshakeInterceptor());
	}
}
