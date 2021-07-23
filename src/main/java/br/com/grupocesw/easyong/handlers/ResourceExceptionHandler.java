package br.com.grupocesw.easyong.handlers;

import br.com.grupocesw.easyong.exceptions.*;
import br.com.grupocesw.easyong.response.dtos.StandardHandlerErrorResponseDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(1)
				.error("Resource not found")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userAlreadyExists(UsernameAlreadyExistsException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(2)
				.error("Username already exists")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userNotExists(UserNotExistException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
				.body(StandardHandlerErrorResponseDto.builder()
					.code(3)
					.error("User not exists")
					.message(ex.getMessage())
					.path(request.getRequestURI())
					.build()
				);
	}

	@ExceptionHandler(UsernameAlreadyConfirmedException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userAlreadyConfirmed(UsernameAlreadyConfirmedException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(4)
				.error("Username already confirmed")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UserNotConfirmedException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userNotConfirmed(UserNotConfirmedException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(5)
				.error("User not confirmed")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(UnauthenticatedUserException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> userNotAuthenticated(UnauthenticatedUserException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(6)
				.error("User not authenticated")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(NotAllowedTypeFileException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> typeFileNotAllowed(NotAllowedTypeFileException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(7)
				.error("Type file not allowed")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(DisabledUserException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> disabledUser(DisabledUserException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(8)
				.error("Disabled user")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(ExpiredTokenRequestException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> expiredToken(ExpiredTokenRequestException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(9)
				.error("Expired token")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> tokenNotFound(TokenNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(10)
				.error("Token not found")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> invalidToken(InvalidTokenException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(11)
				.error("Invalid Token")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(12)
				.error("Invalid request arguments")
				.message(errors)
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(13)
				.error("Argument type mismatch")
				.message(ex.getName() + " should be of type " + ex.getRequiredType().getName())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(14)
				.error("Missing Servlet Request Parameter")
				.message(ex.getParameterName() + " parameter is missing")
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " +
					violation.getPropertyPath() + ": " + violation.getMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(15)
				.error("Constraint Violation")
				.message(errors)
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleInvalidFormatException(InvalidFormatException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(16)
				.error("Invalid format parameter")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<StandardHandlerErrorResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(17)
				.error("Data Integrity Violation")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler({NgoAlreadyExistsException.class})
	public ResponseEntity<StandardHandlerErrorResponseDto> handleNgoAlreadyExistsException(NgoAlreadyExistsException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(18)
				.error("Ngo already exists")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<StandardHandlerErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(19)
				.error("Access denied")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<StandardHandlerErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(20)
				.error("Invalid username or password")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(21)
				.error("Entity not found")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build()
			);
	}
	
}
