/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.ogm.hiking;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

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
		Customer customer = new Customer();
		customer.email = "notanemail";

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Customer>> violations = validator.validate( customer );

		Set<String> messages = violations.stream()
			.map( ConstraintViolation::getMessage )
			.collect( Collectors.toSet() );

		System.out.println( messages );
	}

	@Test
	public void cascadedValidation() {
		Customer customer = new Customer();
		customer.email = "notanemail";

		Order order = new Order();
		order.customer = customer;
		order.orderDate = LocalDate.of( 2017, 5, 11 );
		order.shipmentDate = LocalDate.of( 2017, 5, 7 );

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Order>> violations = validator.validate( order );

		Set<Class<?>> paths = violations.stream()
			.map( v -> v.getConstraintDescriptor().getAnnotation().annotationType() )
			.collect( Collectors.toSet() );

		System.out.println( paths );
	}
}
