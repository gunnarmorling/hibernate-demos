/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.ogm.hiking.model.business;

import com.google.common.collect.Table;

public class CellKey {

	private final Object rowKey;
	private final Object columnKey;

	public static CellKey forCell(Table.Cell<?, ?, ?> cell) {
		return new CellKey( cell.getRowKey(), cell.getColumnKey() );
	}

	public CellKey(Object rowKey, Object columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}

	public Object getRowKey() {
		return rowKey;
	}

	public Object getColumnKey() {
		return columnKey;
	}

	@Override
	public String toString() {
		return "CellKey(" + rowKey + ", " + columnKey + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( columnKey == null ) ? 0 : columnKey.hashCode() );
		result = prime * result + ( ( rowKey == null ) ? 0 : rowKey.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		CellKey other = (CellKey) obj;
		if ( columnKey == null ) {
			if ( other.columnKey != null )
				return false;
		}
		else if ( !columnKey.equals( other.columnKey ) )
			return false;
		if ( rowKey == null ) {
			if ( other.rowKey != null )
				return false;
		}
		else if ( !rowKey.equals( other.rowKey ) )
			return false;
		return true;
	}
}
