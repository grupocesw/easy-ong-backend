package br.com.grupocesw.easyong.handlers;

import br.com.grupocesw.easyong.exceptions.*;
import br.com.grupocesw.easyong.response.dtos.StandardHandlerErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleException(Exception ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(0)
				.error("Unexpected error")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

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
	public ResponseEntity<StandardHandlerErrorResponseDto> invalidToken(InvalidTokenException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(11)
				.error("Invalid Token")
				.message(ex.getMessage())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest resquest) {
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
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(13)
				.error("Argument type mismatch")
				.message(ex.getName() + " should be of type " + ex.getRequiredType().getName())
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest resquest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
			.body(StandardHandlerErrorResponseDto.builder()
				.code(14)
				.error("Missing Servlet Request Parameter")
				.message(ex.getParameterName() + " parameter is missing")
				.path(resquest.getRequestURI())
				.build()
			);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardHandlerErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest resquest) {
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
				.path(resquest.getRequestURI())
				.build()
			);
	}

}
