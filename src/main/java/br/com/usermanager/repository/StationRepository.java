package br.com.usermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.usermanager.model.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

	Station findByName(String name);

}
