package br.com.usermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorValidationDTO {
	
	private String field;
	private String message;
}
