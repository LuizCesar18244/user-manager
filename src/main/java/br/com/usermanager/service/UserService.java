package br.com.usermanager.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usermanager.dto.UserDTO;
import br.com.usermanager.model.Status;
import br.com.usermanager.model.User;
import br.com.usermanager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private StationService stationService;
	

	public UserDTO save(UserDTO dto) {
		User user = new User();
		user = UserDTO.unmarshal(dto, user, profileService, stationService);
		userRepository.save(user);
		return dto;
	}

	public Optional<User> find(String cpf) {
		return userRepository.findById(cpf);
	}

	public Optional<User> delete(String cpf) {
		Optional<User> optionalUser = userRepository.findById(cpf);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			userRepository.delete(user);
		}
		return optionalUser;
	}

	public UserDTO update(String cpf, UserDTO dto) {
		Optional<User> optionalUser = userRepository.findById(cpf);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			UserDTO.unmarshal(dto, user, profileService, stationService);
		}
		return dto;
	}

	public List<UserDTO> listAll() {
		List<User> users = userRepository.findAll();

		List<UserDTO> UsersDTOList = users.stream().map(user -> UserDTO.marshal(user)).collect(Collectors.toList());

		return UsersDTOList;
	}


	public Optional<User> findByName(String name) {
		Optional<User> Optionaluser = userRepository.findByName(name);
		return Optionaluser;
	}

	public List<UserDTO> findByStation(String station) {
		List<User> users = userRepository.findByStationName(station);
		List<UserDTO> UsersDTOList = users.stream().map(user -> UserDTO.marshal(user)).collect(Collectors.toList());
		return UsersDTOList;
	}

	public List<UserDTO> findByProfile(String profile) {
		List<User> users = userRepository.findByProfilesName(profile);
		List<UserDTO> UsersDTOList = users.stream().map(user -> UserDTO.marshal(user)).collect(Collectors.toList());
		return UsersDTOList;
	}

	public List<UserDTO> findByStatus(String statusName) {
		Status status = Status.valueOf(statusName);
		List<User> users = userRepository.findByStatus(status);
		List<UserDTO> UsersDTOList = users.stream().map(user -> UserDTO.marshal(user)).collect(Collectors.toList());
		return UsersDTOList;
	}

	public Optional<User> inativeUser(String cpf) {
		Optional<User> optionalUser = userRepository.findIfIsActive(cpf, Status.ACTIVE.toString());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setStatus(Status.INACTIVE);
		}
		return optionalUser;
	}

	public List<UserDTO> findFemalesBiggerThenEighteen() {
		List<User> users = userRepository.findFemalesBiggerThenEighteen();
		List<UserDTO> UsersDTOList = users.stream().map(user -> UserDTO.marshal(user)).collect(Collectors.toList());
		return UsersDTOList;
	}

	public List<String> findCpfsThatStartsWithZero() {
		return userRepository.findCpfsThatStartsWithZero();
	}

}



