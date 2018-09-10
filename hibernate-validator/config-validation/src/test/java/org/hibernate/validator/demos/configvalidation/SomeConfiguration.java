/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.demos.configvalidation;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SomeConfiguration {

	// property with given name
	@Inject
	@ConfigProperty(name = "some.prop1", defaultValue = "AAA")
	@Size(min=5)
	private String prop1;

	// property with default name
	@Inject
	@ConfigProperty(defaultValue = "1")
	@Min(5)
	private Long prop2;

	// multiple constraints
	@Inject
	@ConfigProperty(name="some.prop3", defaultValue = "not an email and too long")
	@Size(max=10)
	@Email
	private String prop3;

	// Optional
	@Inject
	@ConfigProperty(name = "some.prop4")
	private Optional<@Size(min=2) String> prop4;

	private final int prop5;

	@Inject
	@ConfigProperty(name = "some.prop6", defaultValue = "41")
	private Provider<@Min(42) Integer> prop6;

	@Inject
	public SomeConfiguration(@ConfigProperty(name="some.prop5", defaultValue="43") @Max(42) int prop5) {
		this.prop5 = prop5;
	}

	public String getProp1() {
		return prop1;
	}


	public Long getProp2() {
		return prop2;
	}


	public String getProp3() {
		return prop3;
	}

	public Optional<String> getProp4() {
		return prop4;
	}

	public int getProp5() {
		return prop5;
	}
}
