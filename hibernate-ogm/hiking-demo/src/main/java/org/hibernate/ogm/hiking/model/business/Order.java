package org.hibernate.ogm.hiking.model.business;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Emmanuel Bernard &lt;emmanuel@hibernate.org&gt;
 */
@Entity
@Table(name="TripOrder")
@ValidOrder
public class Order {

	@Id
	@GeneratedValue
	public Long id;

	@NotNull
	public String number;

	public long tripId;

	public LocalDate orderDate;

	public LocalDate shipmentDate;

	@Embedded
	@Valid
	public Customer customer;
}
