package br.com.usermanager.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.usermanager.dto.ErrorValidationDTO;

@RestControllerAdvice
public class ErrorValidationHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorValidationDTO> handle(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getFieldErrors();
		
		List<ErrorValidationDTO> errorValidationDTOList = new ArrayList<ErrorValidationDTO>();
		
		fieldErrors.forEach(fieldError -> {
			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			errorValidationDTOList.add(new ErrorValidationDTO(fieldError.getField(), message));
		});
		
		return errorValidationDTOList;
	}
}
