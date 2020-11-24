package br.com.usermanager.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.usermanager.model.Gender;
import br.com.usermanager.model.Profile;
import br.com.usermanager.model.Station;
import br.com.usermanager.model.Status;
import br.com.usermanager.model.User;
import br.com.usermanager.service.ProfileService;
import br.com.usermanager.service.StationService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	@NotEmpty
	@NotNull
	private String cpf;
	
	@NotEmpty
	@NotNull
	@Size(min = 4)
	private String name;
	
	@NotNull
	private String gender;
		
	@NotEmpty
	@NotNull
	private String birthdate;
	
	@NotEmpty
	@NotNull
	private String station;
	
	@NotEmpty
	@NotNull
	private List<String> profiles;
	
	@NotNull
	private String status;
	
	@NotNull
	String password; 
	
	public static UserDTO marshal(User user) {
		UserDTO dto = new UserDTO();
		
		dto.cpf = user.getCPF();
		dto.name = user.getName();
		
		if(!Objects.isNull(user.getGender())) {
			dto.gender = user.getGender().toString(); 	
		}
		
		if(!Objects.isNull(user.getStatus())) {
			dto.status = user.getStatus().toString();
		}
		
		if(!Objects.isNull( user.getStation())) {
			dto.station = user.getStation().getName().toString();
		}
		
		if(!Objects.isNull(user.getProfiles())) {
			dto.profiles = user.getProfiles().stream().map(profile -> profile.getName()).collect(Collectors.toList());
		}
		
		if(!Objects.isNull(user.getBirthdate())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			dto.birthdate = user.getBirthdate().format(formatter);
		}
		
		return dto;
	}
	
	public static User unmarshal(UserDTO dto, User user, ProfileService profileService, StationService stationService) throws IllegalArgumentException {
		
		user.setCPF(dto.getCpf());
		user.setName(dto.getName());
		
		Gender gender = Gender.valueOf(dto.gender.toUpperCase());
		user.setGender(gender);
		
		Status status = Status.valueOf(dto.status.toUpperCase());
		user.setStatus(status);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate birthdate = LocalDate.parse(dto.birthdate, formatter);
		
		user.setBirthdate(birthdate);

		List<Profile> profiles = new ArrayList<Profile>();
		
		dto.profiles.forEach(profileName -> {
			Profile profile = profileService.findByName(profileName);
			if(!Objects.isNull(profile)) {
				profiles.add(profile);
			}
		});
		
		if(profiles.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		user.setProfiles(profiles);
		
		Station station = stationService.findByName(dto.getStation());
		
		if(Objects.isNull(station)) {
			throw new IllegalArgumentException();
		}
		
		user.setStation(station);
		
		String password = new BCryptPasswordEncoder().encode(dto.password);
		user.setPassword(password);
		
		return user;
		
	}

}




