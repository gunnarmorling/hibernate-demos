package org.hibernate.ogm.hiking.model.business;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Emmanuel Bernard &lt;emmanuel@hibernate.org&gt;
 */
@Entity
@Table(name="TripOrder")
public class Order {

	@Id
	@GeneratedValue
	public Long id;

	public String number;

	public long tripId;

	public LocalDate orderDate;

	public LocalDate shipmentDate;

	@Embedded
	public Customer customer;
}
