package ru.netology.diplom.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage nullPointerException(NullPointerException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				new Date(),
				"External error",
				request.getDescription(false));
		return message;
	}

	@ExceptionHandler(BadRequestException.class)
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