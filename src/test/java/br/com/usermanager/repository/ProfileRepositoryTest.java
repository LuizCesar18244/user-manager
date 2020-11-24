package br.com.usermanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.usermanager.model.Profile;

@DataJpaTest
public class ProfileRepositoryTest {
	
	@Autowired
	private ProfileRepository profileRepository;

	@Test
	public void shouldFindProfileByName() {
		String profileName = "ROLE_ADMINISTRATOR";
		Profile profile = profileRepository.findByName(profileName);
		assertThat(profile).isNotNull();
		assertEquals(profile.getName(), profileName);
	}
	
	@Test
	public void shouldNotFindProfileByNameInvalid() {
		String profileName = "ROLE_INVALID";
		Profile profile = profileRepository.findByName(profileName);
		assertThat(profile).isNull();
	}
}
