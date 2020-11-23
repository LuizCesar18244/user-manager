package br.com.usermanager.config.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.usermanager.model.User;
import br.com.usermanager.repository.UserRepository;

@Service
public class AutenticationService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByName(username);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		throw new  UsernameNotFoundException("Credenciais inválidas");
	}

}
