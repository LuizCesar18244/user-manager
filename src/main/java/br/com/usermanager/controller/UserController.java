package br.com.usermanager.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.usermanager.dto.UserDTO;
import br.com.usermanager.model.User;
import br.com.usermanager.service.UserService;
import br.com.usermanager.util.CPFUtil;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	@CacheEvict(value = "usersList", allEntries = true)
	public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO user) {
		
		if(!CPFUtil.isValidCPF(user.getCpf())) {
			return ResponseEntity.badRequest().build();
		}
		
		Optional<User> optionalUser = userService.find(user.getCpf());
		
		if(optionalUser.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		try {
			UserDTO saved = userService.save(user);
			return new ResponseEntity<UserDTO>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<UserDTO> findByCPF(@PathVariable String cpf) {
		
		Optional<User> optionalUser = userService.find(cpf);

		if (!optionalUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(UserDTO.marshal(optionalUser.get()));
	}

	@DeleteMapping("/{cpf}")
	@CacheEvict(value = "usersList", allEntries = true)
	public ResponseEntity<UserDTO> delete(@PathVariable String cpf) {

		Optional<User> deleted = userService.delete(cpf);

		if (!deleted.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(UserDTO.marshal(deleted.get()));
	}

	@PutMapping("/{cpf}")
	@Transactional
	@CacheEvict(value = "usersList", allEntries = true)
	public ResponseEntity<UserDTO> update(@PathVariable String cpf, @Valid @RequestBody UserDTO user) {
		
		if(!CPFUtil.isValidCPF(user.getCpf()) || !CPFUtil.isValidCPF(cpf)) {
			return ResponseEntity.badRequest().build();
		}

		try {
			UserDTO saved = userService.update(cpf, user);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/list")
	@Cacheable(value = "usersList")
	public ResponseEntity<List<UserDTO>> listAll() {
		List<UserDTO> users = userService.listAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/byName")
	public ResponseEntity<UserDTO> findByName(@RequestParam("name") String userName) {
		Optional<User> optionaUser = userService.findByName(userName);
		if(!optionaUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(UserDTO.marshal(optionaUser.get()));
	}

	@GetMapping("/byStation")
	public ResponseEntity<List<UserDTO>> findByStation(@RequestParam("station") String station) {
		List<UserDTO> users = userService.findByStation(station);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/byProfile")
	public ResponseEntity<List<UserDTO>> findByProfile(@RequestParam("profile") String profile) {
		List<UserDTO> users = userService.findByProfile(profile);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/byStatus")
	public ResponseEntity<List<UserDTO>> findbyStatus(@RequestParam("status") String status) {
		
		try {
			List<UserDTO> users = userService.findByStatus(status);
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/inative/{cpf}")
	@Transactional
	@CacheEvict(value = "usersList", allEntries = true)
	public ResponseEntity<UserDTO> inativeUser(@PathVariable String cpf){
		
		Optional<User> userOptional = userService.inativeUser(cpf);

		if (!userOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(UserDTO.marshal(userOptional.get()));
	}
	
	@GetMapping("/femalesBiggerThenEighteen")
	public ResponseEntity<List<UserDTO>> findFemalesBiggerThenEighteen(){
		List<UserDTO> users = userService.findFemalesBiggerThenEighteen();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/cpfThatStartsWithZero")
	public ResponseEntity<List<String>> findCpfsThatStartsWithZero(){
		List<String> cpfs = userService.findCpfsThatStartsWithZero();
		return ResponseEntity.ok(cpfs);
	}
}







