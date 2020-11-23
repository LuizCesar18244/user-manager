package br.com.usermanager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
	
	@NotNull
	@NotEmpty
	private String password;
	
	@NotNull
	@NotEmpty
	private String name;

	public UsernamePasswordAuthenticationToken adapter(LoginDTO dto) {
		return new UsernamePasswordAuthenticationToken(name, password);
	}

}
