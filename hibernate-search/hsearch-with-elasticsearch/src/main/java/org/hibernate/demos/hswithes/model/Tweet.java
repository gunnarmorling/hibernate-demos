/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.hswithes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed(index = "demo-tweet")
public class Tweet {

	@Id
	@GeneratedValue
	public long id;

	@Field
	@Analyzer(definition = "english")
	public String text;

	@Field(name="when_tweeted", analyze = Analyze.NO)
	public LocalDateTime date;

	@IndexedEmbedded
	@ManyToOne
	public User user;

	@ElementCollection
	@Field
	@IndexedEmbedded
	public List<String> tags = new ArrayList<>();

	public Tweet() {
	}

	public Tweet(String text, User user, LocalDateTime date, String... hashTags) {
		this.text = text;
		this.user = user;
		this.date = date;
		this.tags = hashTags != null ? Arrays.asList( hashTags ) : Collections.emptyList();
	}
}
