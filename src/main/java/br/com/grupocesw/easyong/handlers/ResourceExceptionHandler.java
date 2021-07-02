package br.com.grupocesw.easyong.handlers;

import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.response.dtos.StandardHandlerErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest resquest) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND.value())
			.body(
				StandardHandlerErrorResponseDto.builder()
						.timestamp(LocalDateTime.now())
						.status(HttpStatus.NOT_FOUND.value())
						.error("Resource not found")
						.message(ex.getMessage())
						.path(resquest.getRequestURI())
						.build()
			);
	}

}
