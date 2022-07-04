package ru.netology.diplom.ExceptionHandler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler({BadRequestException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage badRequestException(BadRequestException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		return message;
	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		return message;
	}
}