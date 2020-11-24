package br.com.usermanager.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.usermanager.model.User;

@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	final String CPF = "05936840018";

	@Test
	void  shouldFindUserById() {
		Optional<User> user = userRepository.findById(CPF);
		assertTrue(user.isPresent());
	}
	
	@Test
	void  shouldFindAtleastOneCpf() {
		 List<String> cpfs = userRepository.findCpfsThatStartsWithZero();
		 assertFalse(cpfs.isEmpty());
	}
	
	@Test
	void  shouldNotFindAnyValue() {
		 List<User> users = userRepository.findFemalesBiggerThenEighteen();
		 assertTrue(users.isEmpty());
	}
}
