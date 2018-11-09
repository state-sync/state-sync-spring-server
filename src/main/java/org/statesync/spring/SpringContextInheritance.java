package org.statesync.spring;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.statesync.ThreadContextInheritance;

public class SpringContextInheritance implements ThreadContextInheritance
{

	private Object principal;

	@Override
	public void grab()
	{
		this.principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Override
	public void propagate()
	{
		final SecurityContext ctx = new SecurityContextImpl();
		ctx.setAuthentication(new UsernamePasswordAuthenticationToken(this.principal, null));
		SecurityContextHolder.setContext(ctx);
	}

	@Override
	public void clear()
	{
		SecurityContextHolder.clearContext();
	}

}
