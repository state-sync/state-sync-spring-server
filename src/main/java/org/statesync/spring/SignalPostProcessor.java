package org.statesync.spring;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.statesync.SignalHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SignalPostProcessor implements BeanPostProcessor
{

	private <Model> void invoke(final Method method, final SpringSyncArea<Model> service, final Object... parameters)
	{
		try
		{
			method.invoke(service, parameters);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <Model> Model invokeReturn(final Method method, final SpringSyncArea<Model> service,
			final Object... parameters)
	{
		try
		{
			return (Model) method.invoke(service, parameters);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private SignalHandler<?> newHandler(final SyncSignal ann, final Method method, final SpringSyncArea<?> service)
	{
		final int parameterCount = method.getParameterCount();
		if (parameterCount == 0)
		{
			return (model, user, signal, parameters) -> {
				invoke(method, service);
				return model;
			};
		}
		else if (parameterCount == 2)
		{
			return (model, user, signal, parameters) -> {
				return invokeReturn(method, service, model, user);
			};
		}
		else if (parameterCount == 3)
		{
			final ObjectMapper mapper = new ObjectMapper();
			final Class<?> paramClass = method.getParameters()[2].getType();
			return (model, user, signal, parameters) -> {
				return invokeReturn(method, service, model, user, mapper.convertValue(parameters, paramClass));
			};
		}
		throw new RuntimeException("Unsupported set of parameters");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException
	{
		final Class<?> type = bean.getClass();
		if (type.isAnnotationPresent(SyncAreaService.class))
		{
			for (final Method method : type.getMethods())
			{
				final SyncSignal ann = AnnotationUtils.findAnnotation(method, SyncSignal.class);
				if (ann != null)
				{
					final SpringSyncArea service = (SpringSyncArea) bean;
					service.registerSignalHandler(resolveSignalName(ann, method.getName()),
							newHandler(ann, method, service));
					System.out.println("Found");
				}
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException
	{
		return bean;
	}

	private String resolveSignalName(final SyncSignal ann, final String name)
	{
		return ann.name();
	}

}
