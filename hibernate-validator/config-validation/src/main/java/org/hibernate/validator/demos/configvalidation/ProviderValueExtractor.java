/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.demos.configvalidation;

import javax.inject.Provider;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

/**
 * Obtains values from a CDI {@link Provider}.
 *
 * @author Gunnar Morling
 */
public class ProviderValueExtractor implements ValueExtractor<Provider<@ExtractedValue ?>> {

	@Override
	public void extractValues(Provider<?> originalValue, ValueReceiver receiver) {
		receiver.value( null, originalValue.get() );
	}
}
