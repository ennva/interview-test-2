package eu.cec.digit.comref.interview;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import eu.cec.digit.comref.interview.persistent.domain.InternetServiceProvider;
import eu.cec.digit.comref.interview.persistent.domain.Speed;
import eu.cec.digit.comref.interview.persistent.domain.Speed1;
import eu.cec.digit.comref.interview.persistent.domain.Town;
import eu.cec.digit.comref.interview.persistent.repository.InternetServiceProviderRepository;
import eu.cec.digit.comref.interview.persistent.repository.TownRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class InterviewTest2Application implements CommandLineRunner {
	
	private final static Logger log = LoggerFactory.getLogger(InterviewTest2Application.class);

	@Autowired
	private TownRepository townRepository;

	@Autowired
	private InternetServiceProviderRepository internetServiceProviderRepository;

	public static void main(String[] args) {
		SpringApplication.run(InterviewTest2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Starting test two");

	}

	/*
	 * TOWN
	 */

	public Town addTown(String name, Integer inhabitants, Set<InternetServiceProvider> internetServiceProviders) {

		Town town = new Town();
		town.setName(name);
		town.setInhabitants(inhabitants);
		town.setInternetServiceProviders(internetServiceProviders);

		return townRepository.save(town);

	}

	public Town updateTown(String name, Integer inhabitants, Set<InternetServiceProvider> internetServiceProviders) {

		Town town = getTown(name);
		town.setInhabitants(inhabitants);
		town.setInternetServiceProviders(internetServiceProviders);

		return townRepository.save(town);

	}

	public Town getTown(String name) {

		return townRepository.findById(name).orElse(null);

	}

	public void deleteTown(String name) {

		townRepository.deleteById(name);

	}

	public List<Town> getTowns() {
		
		Pageable paging = PageRequest.of(0, 10);
		Page<Town> page = townRepository.findAll(paging);

		//return townRepository.findAll();
		
		return page.getContent();

	}

	/*
	 * ISP
	 */

	public InternetServiceProvider addInternetServiceProvider(String name, Set<Speed1> speeds, Boolean available) {

		InternetServiceProvider internetServiceProvider = new InternetServiceProvider();
		internetServiceProvider.setAvailable(available);
		internetServiceProvider.setName(name);
		internetServiceProvider.setSpeeds(speeds);

		return internetServiceProviderRepository.save(internetServiceProvider);
	}

	public InternetServiceProvider getInternetServiceProvider(String name) {

		return internetServiceProviderRepository.findById(name).orElse(null);
	}

	public InternetServiceProvider updateInternetServiceProvider(String name, Integer speed, Boolean available) {
		Speed1 speed1 = getSpeedEntity(name, speed);
		
		InternetServiceProvider internetServiceProvider = getInternetServiceProvider(name);
		internetServiceProvider.setAvailable(available);
		internetServiceProvider.setName(name);
		internetServiceProvider.getSpeeds().add(speed1);

		return internetServiceProviderRepository.save(internetServiceProvider);
	}

	public void deleteInternetServiceProvider(String name) {
		internetServiceProviderRepository.deleteById(name);
	}

	public List<InternetServiceProvider> getInternetServiceProviders() {

		Pageable paging = PageRequest.of(0, 10, Sort.by("name").ascending());
		Page<InternetServiceProvider> page = internetServiceProviderRepository.findAll(paging);
		log.info("SIZE With page " + page.getContent().size());

//		List<InternetServiceProvider> isps = internetServiceProviderRepository.findAll(Sort.by(Direction.ASC, "name"));
//		log.info("SIZE " + isps.size());
//		return isps;
		
		return page.getContent();
	}

	public List<InternetServiceProvider> getAvailableInternetServiceProviders() {
		
		return internetServiceProviderRepository.findAll().stream().filter(InternetServiceProvider::getAvailable).collect(Collectors.toList());
	}
	
	private Speed1 getSpeedEntity(String name, Integer speed) {
		Speed1 speedObj = new Speed1();
		speedObj.setIspName(name);
		speedObj.setSpeed(speed);
		
		return speedObj;
	}

}
