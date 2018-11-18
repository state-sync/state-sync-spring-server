package org.statesync.spring;

import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.statesync.spring.ws.interceptors.HttpSessionIdHandshakeInterceptor;

public abstract class AbstractStateSyncConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer
{
	protected abstract TaskScheduler getMessageBrokerTaskScheduler();

	public AbstractStateSyncConfig()
	{
		super();
	}

	@Override
	public void configureMessageBroker(final MessageBrokerRegistry registry)
	{
		registry.enableSimpleBroker("/out") //
				.setHeartbeatValue(new long[] { 10000, 20000 }).setTaskScheduler(this.getMessageBrokerTaskScheduler());
		registry.setApplicationDestinationPrefixes("/app");
	}

	protected String getAllowedOrigins()
	{
		return "*";
	}

	protected String getEndpointPath()
	{
		return "/state-sync";
	}

	@Override
	public void configureClientOutboundChannel(final ChannelRegistration registration)
	{
		// temporary solution
		registration.taskExecutor().corePoolSize(1);
	}

	@Override
	public void customizeClientInboundChannel(final ChannelRegistration registration)
	{
		// temporary solution
		registration.taskExecutor().corePoolSize(1);
	}

	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry)
	{
		registry.addEndpoint(getEndpointPath()).setAllowedOrigins(getAllowedOrigins())
				.addInterceptors(new HttpSessionIdHandshakeInterceptor());
	}
}
