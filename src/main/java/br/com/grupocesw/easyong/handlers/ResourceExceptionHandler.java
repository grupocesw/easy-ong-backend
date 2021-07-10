package br.com.grupocesw.easyong.handlers;

import br.com.grupocesw.easyong.exceptions.*;
import br.com.grupocesw.easyong.response.dtos.StandardHandlerErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(1)
				.error("Resource not found")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userAlreadyExists(UsernameAlreadyExistsException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(2)
				.error("Username already exists")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userNotExists(UserNotExistException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
				.body(StandardHandlerErrorResponseDto.builder()
					.code(3)
					.error("User not exists")
					.message(ex.getMessage())
					.path(resquest.getRequestURI())
					.build()
				);
	}

	@ExceptionHandler(UsernameAlreadyConfirmedException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userAlreadyConfirmed(UsernameAlreadyConfirmedException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(4)
				.error("Username already confirmed")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UserNotConfirmedException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userNotConfirmed(UserNotConfirmedException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(5)
				.error("User not confirmed")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UnauthenticatedUserException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userNotAuthenticated(UnauthenticatedUserException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(6)
				.error("User not authenticated")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(NotAllowedTypeFileException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> typeFileNotAllowed(NotAllowedTypeFileException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(7)
				.error("Type file not allowed")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(DisabledUserException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> disabledUser(DisabledUserException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
				.body(StandardHandlerErrorResponseDto.builder()
					.code(8)
					.error("Disabled user")
					.message(ex.getMessage())
					.path(resquest.getRequestURI())
					.build()
				);
	}

	@ExceptionHandler(ExpiredTokenRequestException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> expiredToken(ExpiredTokenRequestException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
				.body(StandardHandlerErrorResponseDto.builder()
					.code(9)
					.error("Expired token")
					.message(ex.getMessage())
					.path(resquest.getRequestURI())
					.build()
				);
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> tokenNotFound(TokenNotFoundException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(10)
				.error("Token not found")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> invalideToken(InvalidTokenException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(11)
				.error("Invalid Token")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

}
