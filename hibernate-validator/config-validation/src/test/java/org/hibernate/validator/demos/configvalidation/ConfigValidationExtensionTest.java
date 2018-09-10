package org.hibernate.validator.demos.configvalidation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.validation.ConstraintViolationException;

import org.jboss.weld.exceptions.DeploymentException;
import org.junit.After;
import org.junit.Test;

public class ConfigValidationExtensionTest {

	@After
	public void resetSystemProperties() {
		System.setProperty( "some.prop1", "" );
		System.setProperty( "org.hibernate.validator.demos.configvalidation.SomeConfiguration.prop2", "" );
		System.setProperty( "some.prop3", "" );
		System.setProperty( "some.prop4", "" );
		System.setProperty( "some.prop6", "" );
	}

	@Test
	public void shouldFailDeploymentDueToInvalidConfigValues() {
		System.setProperty( "some.prop4", "1" );

		SeContainerInitializer containerInit = SeContainerInitializer.newInstance();

		try {
			SeContainer container = containerInit.initialize();
			container.getBeanManager().fireEvent(new SomeEvent());
			container.close();
			fail( "Expected exception wasn't raised" );
		}
		catch(DeploymentException e) {
			assertTrue( e.getMessage().contains( "prop1: size must be between 5 and 2147483647" ) );
			assertTrue( e.getMessage().contains( "prop2: must be greater than or equal to 5" ) );
			// prop3: unordered set of two violations
			assertTrue( e.getMessage().contains( "must be a well-formed email address" ) );
			assertTrue( e.getMessage().contains( "size must be between 0 and 10" ) );
			assertTrue( e.getMessage().contains( "prop4: size must be between 2 and 2147483647" ) );
			assertTrue( e.getMessage().contains( "prop6: must be greater than or equal to 42" ) );
		}
	}

	@Test
	public void constructorValidationShouldBeApplied() {
		System.setProperty( "some.prop1", "AAAAA" );
		System.setProperty( "org.hibernate.validator.demos.configvalidation.SomeConfiguration.prop2", "5" );
		System.setProperty( "some.prop3", "bob@a.com" );
		System.setProperty( "some.prop4", "AA" );
		System.setProperty( "some.prop6", "43" );

		SeContainerInitializer containerInit = SeContainerInitializer.newInstance();

		try {
			SeContainer container = containerInit.initialize();
			container.getBeanManager().fireEvent(new SomeEvent());
			container.close();
			fail( "Expected exception wasn't raised" );
		}
		catch(ConstraintViolationException e) {
			assertTrue( e.getMessage().contains( "must be less than or equal to 42" ) );
		}
	}
}
