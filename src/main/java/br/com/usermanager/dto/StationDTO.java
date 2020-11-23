package br.com.usermanager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.usermanager.model.Station;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationDTO {
	
	private Long id;
	
	@NotEmpty
	@NotNull
	private String name;
	
	
	public static StationDTO marshal(Station station) {
		StationDTO dto = new StationDTO();
		dto.id = station.getId();
		dto.name = station.getName();
		
		return dto; 
	}

}
