package eu.cec.digit.comref.interview;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.cec.digit.comref.interview.persistent.domain.InternetServiceProvider;
import eu.cec.digit.comref.interview.persistent.domain.Town;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class InterviewTest2ApplicationTests {
	
	private final static Logger log = LoggerFactory.getLogger(InterviewTest2ApplicationTests.class);

	@Autowired
	private InterviewTest2Application interviewTest2Application;

	@AfterEach
	public void cleanup() {

		log.info("cleaning up");

		interviewTest2Application.getTowns().stream().forEach(t -> interviewTest2Application.deleteTown(t.getName()));
		interviewTest2Application.getInternetServiceProviders().stream()
				.forEach(t -> interviewTest2Application.deleteInternetServiceProvider(t.getName()));

		assertTrue(interviewTest2Application.getTowns().isEmpty());
		assertTrue(interviewTest2Application.getInternetServiceProviders().isEmpty());

	}

	@Test
	public void testBasicInternetServiceProviderCrud() {

		interviewTest2Application.addInternetServiceProvider("post", 1000, true);
		interviewTest2Application.addInternetServiceProvider("eltrona", 100, false);
		interviewTest2Application.addInternetServiceProvider("luxonline", 10, true);

		InternetServiceProvider internetServiceProvider = interviewTest2Application.getInternetServiceProvider("post");
		assertNotNull(internetServiceProvider);
		assertTrue(internetServiceProvider.getName().equals("post"));
		assertTrue(internetServiceProvider.getSpeed().equals(1000));
		assertTrue(internetServiceProvider.getAvailable());

		internetServiceProvider = interviewTest2Application.getInternetServiceProvider("eltrona");
		assertNotNull(internetServiceProvider);
		assertTrue(internetServiceProvider.getName().equals("eltrona"));
		assertTrue(internetServiceProvider.getSpeed().equals(100));
		assertFalse(internetServiceProvider.getAvailable());

		internetServiceProvider = interviewTest2Application.getInternetServiceProvider("luxonline");
		assertNotNull(internetServiceProvider);
		assertTrue(internetServiceProvider.getName().equals("luxonline"));
		assertTrue(internetServiceProvider.getSpeed().equals(10));
		assertTrue(internetServiceProvider.getAvailable());
	}

	@Test
	public void testBasicTownCrud() {

		interviewTest2Application.addTown("Luxembourg", 114303, null);
		interviewTest2Application.addTown("Esch-sur-Alzette", 28228, null);
		interviewTest2Application.addTown("Dudelange", 18013, null);

		Town town = interviewTest2Application.getTown("Luxembourg");
		assertNotNull(town);
		assertTrue(town.getName().equals("Luxembourg"));
		assertTrue(town.getInhabitants().equals(114303));

		town = interviewTest2Application.getTown("Esch-sur-Alzette");
		assertNotNull(town);
		assertTrue(town.getName().equals("Esch-sur-Alzette"));
		assertTrue(town.getInhabitants().equals(28228));

		town = interviewTest2Application.getTown("Dudelange");
		assertNotNull(town);
		assertTrue(town.getName().equals("Dudelange"));
		assertTrue(town.getInhabitants().equals(18013));

	}

	@Test
	public void testIspTownRelation() {

		testBasicInternetServiceProviderCrud();

		InternetServiceProvider internetServiceProvider = interviewTest2Application.getInternetServiceProvider("post");
		interviewTest2Application.addTown("Luxembourg", 114303, internetServiceProvider);

		Town town = interviewTest2Application.getTown("Luxembourg");

		assertNotNull(town);
		assertTrue(town.getInternetServiceProvider().getName().equals("post"));

	}

	@Test
	public void testSortedInternetServiceProviderCrud() {

		testBasicInternetServiceProviderCrud();
		List<InternetServiceProvider> list = interviewTest2Application.getInternetServiceProviders();

		assertTrue(list.get(0).getName().equals("eltrona"));
		assertTrue(list.get(1).getName().equals("luxonline"));
		assertTrue(list.get(2).getName().equals("post"));

	}

	@Test
	public void testRetrieveOnlyAvailableIsp() {

		testBasicInternetServiceProviderCrud();
		List<InternetServiceProvider> list = interviewTest2Application.getAvailableInternetServiceProviders();

		assertTrue(list.size() == 2);
		assertTrue(list.stream().anyMatch(f -> f.getName().equals("post")));
		assertTrue(list.stream().anyMatch(f -> f.getName().equals("luxonline")));
	}
}
