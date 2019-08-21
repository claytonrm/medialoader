package com.roihunter.medialoader.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {

	private HttpStatus status;
	private String message;
	private String debugMessage;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(final HttpStatus status) {
		this();
		this.status = status;
	}

	ApiError(final HttpStatus status, final Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	ApiError(final HttpStatus status, final String message, final Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

}
