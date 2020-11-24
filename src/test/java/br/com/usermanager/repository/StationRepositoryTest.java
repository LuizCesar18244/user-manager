package br.com.usermanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.usermanager.model.Station;

@DataJpaTest
class StationRepositoryTest {
	
	@Autowired
	private StationRepository stationRepository;

	@Test
	public void shouldFindStationByName() {
		String stationName = "MANAGER";
		 Station station = stationRepository.findByName(stationName);
		assertThat(station).isNotNull();
		assertEquals(station.getName(), stationName);
	}
	
	@Test
	public void shouldNotFindStationeByNameInvalid() {
		String stationName = "INVALID";
		Station station = stationRepository.findByName(stationName);
		assertThat(station).isNull();
	}

}
