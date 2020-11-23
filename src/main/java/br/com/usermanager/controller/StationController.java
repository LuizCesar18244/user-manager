package br.com.usermanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.usermanager.dto.StationDTO;
import br.com.usermanager.service.StationService;
import br.com.usermanager.util.StringUtil;

@RestController
@RequestMapping("api/station")
public class StationController {
	
	@Autowired
	private StationService stationService;
	
	@PostMapping
	public ResponseEntity<StationDTO> save(@Valid @RequestBody StationDTO dto) {
		String name = StringUtil.normalizeAccents(dto.getName()).toUpperCase().trim();
		StationDTO saved = stationService.save(name);
		return new ResponseEntity<StationDTO>(saved, HttpStatus.CREATED);
	}
}
