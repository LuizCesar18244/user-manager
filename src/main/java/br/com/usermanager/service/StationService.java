package br.com.usermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usermanager.dto.StationDTO;
import br.com.usermanager.model.Station;
import br.com.usermanager.repository.StationRepository;

@Service
public class StationService {
	
	@Autowired
	private StationRepository stationRepository;
	
	public Station findByName(String name) {
		Station station = stationRepository.findByName(name);
		return station;
	}

	public StationDTO save(String name) {
		Station station = new Station(name);
		Station saved = stationRepository.save(station);
		return StationDTO.marshal(saved);
	}

}
