/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demos;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.demos.hswithes.model.Tweet;
import org.hibernate.demos.hswithes.model.User;
import org.hibernate.search.elasticsearch.ElasticsearchQueries;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.engine.spi.QueryDescriptor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateSearchWithElasticsearchIT extends TestBase {

	private static EntityManagerFactory emf;

	@BeforeClass
	public static void setUpEmf() {
		emf = Persistence.createEntityManagerFactory( "videoGamePu" );
		setUpTestData();
	}

	public static void setUpTestData() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {

			User gunnar = new User( "Gunnar" );
			em.persist( gunnar );

			User thorben = new User( "Thorben" );
			em.persist( thorben );

			Tweet t1 = new Tweet(
					"Hibernate ORM 5.2.15 is out!",
					gunnar,
					dateTime( 2018, 3, 10, 12, 37, 5 ),
					"hibernate", "release"
			);
			Tweet t2 = new Tweet(
					"Hibernate Validator 6.0.8 Released!",
					gunnar,
					dateTime( 2018, 3, 9, 10, 37, 15 ),
					"hibernate validator", "release"
			);
			Tweet t3 = new Tweet(
					"The Ultimate Guide to JPQL Queries with JPA",
					thorben,
					dateTime( 2018, 2, 27, 8, 42, 5 ),
					"tutorial"
			);
			Tweet t4 = new Tweet(
					"How to validate JPA entities",
					thorben,
					dateTime( 2018, 3, 1, 14, 13, 5 ),
					"tutorial"
			);


			em.persist( t1 );
			em.persist( t2 );
			em.persist( t3 );
			em.persist( t4 );
		} );

		em.close();
	}

	private static LocalDateTime dateTime(int year, int month, int day, int hour, int minute, int second) {
		return LocalDateTime.of( year, month, day, hour, minute, second, 0 );
	}

	@Test
	public void wildcardQuery() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( Tweet.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.keyword().wildcard().onFields( "text" ).matching( "valid*" ).createQuery(),
					Tweet.class
			);

			List<Tweet> tweets = query.getResultList();

			tweets.stream()
				.map( t -> t.id + " - " + t.text )
				.forEach( System.out::println );
		} );

		em.close();
	}

	@Test
	public void rangeQuery() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( Tweet.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.range()
					.onField( "when_tweeted" )
					.from( dateTime( 2018, 2, 27, 0, 0, 0 ) )
					.to( dateTime( 2018, 3, 3, 0, 0, 0 ) )
					.createQuery(),
					Tweet.class
			);

			List<Tweet> tweets = query.getResultList();

			tweets.stream()
				.map( t -> t.id + " - " + t.date )
				.forEach( System.out::println );
		} );

		em.close();
	}

	@Test
	public void nativeQuery() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );

			QueryDescriptor esQuery = ElasticsearchQueries.fromJson(
				      "{ 'query': { 'match' : { 'user.name' : 'thorben' } } }");

			FullTextQuery query = ftem.createFullTextQuery( esQuery, Tweet.class );

			List<Tweet> tweets = query.getResultList();

			tweets.stream()
				.map( t -> t.id + " - " + t.user.name )
				.forEach( System.out::println );
		} );

		em.close();
	}

	@AfterClass
	public static void closeEmf() {
		if ( emf != null ) {
			emf.close();
		}
	}
}
