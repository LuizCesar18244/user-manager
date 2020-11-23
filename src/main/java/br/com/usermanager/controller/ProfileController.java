package br.com.usermanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.usermanager.dto.ProfileDTO;
import br.com.usermanager.service.ProfileService;
import br.com.usermanager.util.StringUtil;

@RestController
@RequestMapping("api/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@PostMapping
	public ResponseEntity<ProfileDTO> save(@Valid @RequestBody ProfileDTO dto) {
		String name = StringUtil.normalizeAccents(dto.getName()).toUpperCase().trim();
		ProfileDTO saved = profileService.save(name);
		return new ResponseEntity<ProfileDTO>(saved, HttpStatus.CREATED);
	}
}
