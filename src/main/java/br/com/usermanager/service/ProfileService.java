package br.com.usermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usermanager.dto.ProfileDTO;
import br.com.usermanager.model.Profile;
import br.com.usermanager.repository.ProfileRepository;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	public Profile findByName (String name) {
		Profile profile = profileRepository.findByName(name);
		return profile;
	}

	public ProfileDTO save(String name) {
		Profile profile = new Profile(name);
		Profile saved = profileRepository.save(profile);
		return ProfileDTO.marshal(saved);
	}
}
