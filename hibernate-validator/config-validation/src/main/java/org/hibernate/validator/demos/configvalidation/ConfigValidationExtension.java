/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.demos.configvalidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.DeploymentException;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.PropertyDescriptor;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * @author Gunnar Morling
 *
 */
public class ConfigValidationExtension implements Extension {

	private final Validator validator;

	/**
	 * All injection points that have at least one BV constraint.
	 */
	private final Set<InjectionPoint> constrainedInjectionPoints;

	public ConfigValidationExtension() {
		this.validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		this.constrainedInjectionPoints = new HashSet<>();
	}
	public void collectConstrainedConfigProperties(@Observes ProcessInjectionPoint<?, ?> pip) {
		ConfigProperty configProperty = pip.getInjectionPoint()
				.getAnnotated()
				.getAnnotation(ConfigProperty.class);

		if (configProperty != null) {
			PropertyDescriptor constraints = validator.getConstraintsForClass(pip.getInjectionPoint().getBean().getBeanClass()).getConstraintsForProperty( pip.getInjectionPoint().getMember().getName() );

			if (constraints != null) {
				constrainedInjectionPoints.add(pip.getInjectionPoint());
			}
		}
	}

	public void validate(@Observes AfterDeploymentValidation add, BeanManager bm) {
		List<String> deploymentProblems = new ArrayList<>();

		for (InjectionPoint injectionPoint : constrainedInjectionPoints) {
			Object injectableReference = bm.getInjectableReference( injectionPoint, bm.createCreationalContext( injectionPoint.getBean() ) );
			Set<? extends ConstraintViolation<?>> violations = validator.validateValue( injectionPoint.getBean().getBeanClass(), injectionPoint.getMember().getName(), injectableReference );

			if (!violations.isEmpty()) {
				String message = violations.stream()
						.map( ConstraintViolation::getMessage )
						.collect( Collectors.joining("; ") );

				deploymentProblems.add(injectionPoint.getMember().getName() + ": " + message);
			}
		}

		if (!deploymentProblems.isEmpty()) {
			add.addDeploymentProblem(new DeploymentException("Found invalid configuration value(s): \n"
					+ String.join("\n", deploymentProblems)));
		}

	}
}
