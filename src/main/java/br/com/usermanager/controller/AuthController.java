package br.com.usermanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.usermanager.config.security.TokenService;
import br.com.usermanager.dto.LoginDTO;
import br.com.usermanager.dto.TokenDTO;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDTO> auth( @RequestBody @Valid LoginDTO dto) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = dto.adapter(dto); 
		try {
			Authentication authentication = authManager.authenticate(usernamePasswordAuthenticationToken);
			String token = tokenService.generateToken(authentication);
			
			return ResponseEntity.ok( new TokenDTO(token , "Bearer"));
			
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	 
}
