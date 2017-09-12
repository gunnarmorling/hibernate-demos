/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.ogm.hiking;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.ogm.hiking.model.business.Customer;
import org.hibernate.ogm.hiking.model.business.Order;
import org.junit.Test;

/**
 * @author Gunnar Morling
 *
 */
public class ValidationTest {

	@Test
	public void simpleValidation() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Customer c = new Customer();
		c.email = "notvalid";

		Order o = new Order();
		o.customer = c;

		o.orderDate = LocalDate.of( 2017, 8, 12 );
		o.shipmentDate = LocalDate.of( 2017, 7, 12 );

		Set<ConstraintViolation<Order>> violations = validator.validate( o );

		for ( ConstraintViolation<Order> constraintViolation : violations ) {
			System.out.println( "=======" );
			System.out.println( "Message: " + constraintViolation.getMessage() );
			System.out.println( "Property path: " + constraintViolation.getPropertyPath() );
			System.out.println( "Annotation: " + constraintViolation.getConstraintDescriptor().getAnnotation().annotationType() );
		}
	}
}
