package ru.netology.diplom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler({BadRequestException.class, ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
			MissingServletRequestPartException.class, MethodArgumentNotValidException.class, IllegalArgumentException.class,
			NoSuchElementException.class, EntityNotFoundException.class, MissingRequestValueException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage badRequestException(Exception ex, WebRequest request) {
		return new ErrorMessage(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
		return new ErrorMessage(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
	}
}