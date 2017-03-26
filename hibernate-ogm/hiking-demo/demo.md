# Demo

## Simple Validation
* Add @Email to Customer

    Customer customer = new Customer();
		customer.email = "notanemail";

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Customer>> violations = validator.validate( customer );

		Set<String> messages = violations.stream()
			.map( ConstraintViolation::getMessage )
			.collect( Collectors.toSet() );

		System.out.println( messages );

## Cascaded Validation

* Add @Valid to Order

## Custom Constraint

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(ValidOrder.List.class)
@Constraint( validatedBy = ValidOrder.OrderNumberValidator.class )
public @interface ValidOrder {

	String message() default "Invalid order shipment date must be after order date";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@interface List {
		ValidOrder[] value();
	}

	class OrderNumberValidator implements ConstraintValidator<ValidOrder, Order> {

		@Override
		public boolean isValid(Order value, ConstraintValidatorContext context) {
			if ( value == null || value.orderDate == null || value.shipmentDate == null ) {
				return true;
			}

			return value.shipmentDate.isAfter( value.orderDate );
		}
	}
}

## Automatic Validation

* Show JPA failure

http POST http://localhost:8080/hibernate-ogm-hiking-demo-1.0-SNAPSHOT/hiking-manager/orders < src/script/orderCreation.json

* Add @Valid to OrderResource#createOrder()

## Client-side Validation

http http://localhost:8080/hibernate-ogm-hiking-demo-1.0-SNAPSHOT/webresources/validation
