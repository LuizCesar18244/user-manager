package br.com.usermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.usermanager.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

	public Profile findByName(String name);
}
