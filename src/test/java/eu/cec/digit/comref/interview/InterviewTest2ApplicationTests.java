package eu.cec.digit.comref.interview;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.cec.digit.comref.interview.persistent.domain.InternetServiceProvider;
import eu.cec.digit.comref.interview.persistent.domain.Speed1;
import eu.cec.digit.comref.interview.persistent.domain.Technician;
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
		interviewTest2Application.getTechnicians().stream().forEach(tech -> interviewTest2Application.deleteTechnician(tech.getName()));

		assertTrue(interviewTest2Application.getTowns().isEmpty());
		assertTrue(interviewTest2Application.getInternetServiceProviders().isEmpty());
		assertTrue(interviewTest2Application.getTechnicians().isEmpty());

	}

	@Test
	public void testBasicInternetServiceProviderCrud() {

		Set<Speed1> speeds1 = new HashSet<>();
		speeds1.add(new Speed1("post", 1000));
		speeds1.add(new Speed1("post", 10000));

		Set<Speed1> speeds2 = new HashSet<>();
		speeds2.add(new Speed1("eltrona", 100));
		speeds2.add(new Speed1("eltrona", 1000));

		Set<Speed1> speeds3 = new HashSet<>();
		speeds3.add(new Speed1("luxonline", 10));
		speeds3.add(new Speed1("luxonline", 100));

		interviewTest2Application.addInternetServiceProvider("post", speeds1, true);
		interviewTest2Application.addInternetServiceProvider("eltrona", speeds2, false);
		interviewTest2Application.addInternetServiceProvider("luxonline", speeds3, true);

		InternetServiceProvider internetServiceProvider = interviewTest2Application.getInternetServiceProvider("post");
		assertNotNull(internetServiceProvider);
		assertTrue(internetServiceProvider.getName().equals("post"));
		assertTrue(internetServiceProvider.getSpeeds().stream().anyMatch(s -> s.getSpeed().equals(1000)));
		assertTrue(internetServiceProvider.getSpeeds().stream().filter(s -> s.getSpeed() == 1000)
				.allMatch(s -> s.getSpeed().equals(1000)));
		assertTrue(internetServiceProvider.getSpeeds().size() == 2);
		assertTrue(internetServiceProvider.getAvailable());

		internetServiceProvider = interviewTest2Application.getInternetServiceProvider("eltrona");
		assertNotNull(internetServiceProvider);
		assertTrue(internetServiceProvider.getName().equals("eltrona"));
		assertTrue(internetServiceProvider.getSpeeds().stream().anyMatch(s -> s.getSpeed().equals(100)));
		assertFalse(internetServiceProvider.getAvailable());

		internetServiceProvider = interviewTest2Application.getInternetServiceProvider("luxonline");
		assertNotNull(internetServiceProvider);
		assertTrue(internetServiceProvider.getName().equals("luxonline"));
		assertTrue(internetServiceProvider.getSpeeds().stream().anyMatch(s -> s.getSpeed().equals(10)));
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
	public void testBasicTechnicianCrud() {

		interviewTest2Application.addTechnician("Bob", "wirer");
		interviewTest2Application.addTechnician("Dylan", "liner");

		Technician technician = interviewTest2Application.getTechnician("Bob");
		assertNotNull(technician);
		assertTrue(technician.getName().equals("Bob"));
		assertTrue(technician.getSkill().equals("wirer"));

		technician = interviewTest2Application.getTechnician("Dylan");
		assertNotNull(technician);
		assertTrue(technician.getName().equals("Dylan"));
		assertTrue(technician.getSkill().equals("liner"));

	}
	
	@Test
	public void testTownTechnicianRelation() {
		testBasicTechnicianCrud();
		
		Technician bob = interviewTest2Application.getTechnician("Bob");
		Technician dylan = interviewTest2Application.getTechnician("Dylan");
		
		Technician neph = interviewTest2Application.addTechnician("neph", "tuttofare");
		
		interviewTest2Application.addTown("Luxembourg", 12000, new HashSet<InternetServiceProvider>(), new ArrayList<Technician>());
		interviewTest2Application.addTown("Ferrara", 22000, new HashSet<InternetServiceProvider>(), new ArrayList<Technician>());
		
		Town town = interviewTest2Application.getTown("Luxembourg");
		town.getTechnicians().add(bob);
		town.getTechnicians().add(dylan);
		town.getTechnicians().add(neph);
		
		Town ferrara = interviewTest2Application.getTown("Ferrara");
		ferrara.getTechnicians().add(neph);
		ferrara.getTechnicians().add(bob);
		
		
		assertNotNull(town);
		assertTrue(town.getTechnicians().size() == 3);
		assertTrue(town.getTechnicians().stream().filter(t -> t.getName().equalsIgnoreCase("bob")).allMatch(t -> t.getName().equalsIgnoreCase("bob")));
		assertTrue(town.getTechnicians().stream().anyMatch(t -> t.getName().equalsIgnoreCase("neph")));
		assertTrue(ferrara.getTechnicians().size() == 2);
		
		neph.getTowns().add(ferrara);
		neph.getTowns().add(town);
		assertTrue(neph.getTowns().contains(ferrara));	
		assertTrue(neph.getTowns().size() == 2);
	}

	@Test
	public void testIspTownRelation() {

		testBasicInternetServiceProviderCrud();

		InternetServiceProvider post = interviewTest2Application.getInternetServiceProvider("post");
		InternetServiceProvider eltrona = interviewTest2Application.getInternetServiceProvider("eltrona");
		InternetServiceProvider luxonline = interviewTest2Application.getInternetServiceProvider("luxonline");
		Set<InternetServiceProvider> isps = new HashSet<>();
		isps.add(post);
		isps.add(eltrona);
		isps.add(luxonline);

		interviewTest2Application.addTown("Luxembourg", 114303, isps);

		Town town = interviewTest2Application.getTown("Luxembourg");

		assertNotNull(town);
		assertTrue(town.getInternetServiceProviders().stream().filter(isp -> isp.getName().equals("eltrona"))
				.allMatch(isp -> isp.getName().equals("eltrona")));
		assertTrue(town.getInternetServiceProviders().size() == 3);

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
