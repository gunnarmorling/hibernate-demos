/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.ogm.hiking.model.business;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

/**
 * @author Gunnar Morling
 *
 */
public class TableCellExtractor implements ValueExtractor<Table<?, ?, @ExtractedValue ?>> {

	@Override
	public void extractValues(Table<?, ?, ?> originalValue, ValueReceiver receiver) {
		for ( Cell<?, ?, ?> cell : originalValue.cellSet() ) {
			receiver.keyedValue( "<table cell>", CellKey.forCell( cell ), cell.getValue() );
		}
	}
}
