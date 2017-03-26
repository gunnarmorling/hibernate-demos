/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.ogm.hiking;

import java.time.Year;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Positive;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author Gunnar Morling
 */
public class CustomExtractorTest {

	@Test
	public void guavaTableCellExtractor() {
		RevenueData revenueData = new RevenueData();

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<RevenueData>> violations = validator.validate( revenueData );

		for ( ConstraintViolation<RevenueData> constraintViolation : violations ) {
			System.out.println( constraintViolation.getPropertyPath() );
		}
	}

	public static class RevenueData {

		private final Table<Year, String, @Positive Integer> revenuePerYearAndCategory;

		public RevenueData() {
			revenuePerYearAndCategory = HashBasedTable.create();

			revenuePerYearAndCategory.put( Year.of( 2016 ), "books", 200 );
			revenuePerYearAndCategory.put( Year.of( 2016 ), "dvds", 300 );
			revenuePerYearAndCategory.put( Year.of( 2016 ), "floppy disks", -100 );
		}
	}
}
