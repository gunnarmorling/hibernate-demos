/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.demos.configvalidation;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class SomeBean {

	@Inject
	private SomeConfiguration config;

	public void onEvent(@Observes SomeEvent ignored) {
		System.out.println( "Hello, " + config.getProp1() );
	}
}
