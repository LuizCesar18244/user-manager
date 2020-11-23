package br.com.usermanager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.usermanager.model.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
	
	private Long id;
	
	@NotNull
	@NotEmpty
	private String name;
	
	
	public static ProfileDTO marshal(Profile profile) {
		ProfileDTO dto = new ProfileDTO();
		dto.id = profile.getId();
		dto.name = profile.getName();
		
		return dto; 
	}

}
