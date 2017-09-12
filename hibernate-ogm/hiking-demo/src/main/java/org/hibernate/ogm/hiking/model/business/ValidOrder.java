/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.ogm.hiking.model.business;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
/**
 * @author Gunnar Morling
 *
 */
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(ValidOrder.List.class)
@Constraint( validatedBy = ValidOrder.OrderValidator.class )
public @interface ValidOrder {

	String message() default "Invalid order shipment date must be after order date";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@interface List {

		ValidOrder[] value();
	}

	class OrderValidator implements ConstraintValidator<ValidOrder, Order> {

		@Override
		public boolean isValid(Order value, ConstraintValidatorContext context) {
			if ( value == null || value.orderDate == null || value.shipmentDate == null ) {
				return true;
			}

			return value.shipmentDate.isAfter( value.orderDate );
		}
	}
}
