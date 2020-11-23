package br.com.usermanager.config.security;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.usermanager.model.User;
import br.com.usermanager.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter{
	
	@NonNull
	private TokenService tokenService;
	
	@NonNull
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recoverToken(request);
		
		boolean isValid = tokenService.isValid(token);
		
		if(isValid) {
			authUser(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void authUser(String token) {
		String cpf =  tokenService.getUserCpf(token);
		Optional<User> optionalUser = userRepository.findById(cpf);
		
		if(optionalUser.isPresent() && optionalUser.get().isEnabled() ) {
			User user = optionalUser.get();
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(Objects.isNull(token) || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
