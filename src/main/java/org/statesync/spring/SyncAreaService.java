package org.statesync.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Service
public @interface SyncAreaService {
	/**
	 * Prefix of local client side property
	 *
	 * @return
	 */
	String clientLocalPrefix() default "$";

	/**
	 * Prefixes of JsonPaths pushed by client. Everything is pushed by default.
	 *
	 * @return
	 */
	String[] clientPush() default { "/" };

	/**
	 * Area id
	 *
	 * @return
	 */
	String id();

	/**
	 * Area model class
	 *
	 * @return
	 */
	Class<?> model();

	/**
	 * Prefix of local server side property
	 *
	 * @return
	 */
	String serverLocalPrefix() default "$";

	/**
	 * Prefixes of JsonPaths pushed by server. Everything is pushed by default.
	 *
	 * @return
	 */
	String[] serverPush() default { "/" };
}
