/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.ogm.hiking.model;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

import org.hibernate.ogm.hiking.model.business.CellKey;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

/**
 * @author Gunnar Morling
 *
 */
public class TableCellValueExtractor implements ValueExtractor<Table<?, ? , @ExtractedValue ?>> {

	@Override
	public void extractValues(Table<?, ?, ?> originalValue, ValueExtractor.ValueReceiver receiver) {
		for ( Cell<?, ?, ?> cell : originalValue.cellSet() ) {
			receiver.keyedValue( "<table_cell>", new CellKey( cell.getRowKey(), cell.getColumnKey() ), cell.getValue() );
		}
	}
}
