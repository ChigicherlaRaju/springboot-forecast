package com.boot.forecast.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "City Not Found")
public class CityNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;

	/** The status code. */
	private int statusCode;

	/**
	 * Instantiates a new city not found exception.
	 */
	public CityNotFoundException() {
		super();
	}

	/**
	 * Instantiates a new city not found exception.
	 *
	 * @param msg the message
	 */
	public CityNotFoundException(String msg) {
		super(msg);
		this.message = msg;
	}

	/**
	 * Instantiates a new city not found exception.
	 *
	 * @param msg  the message
	 * @param code the code
	 */
	public CityNotFoundException(String msg, int code) {
		super(msg);
		this.message = msg;
		this.statusCode = code;
	}

	/**
	 * Instantiates a new city not found exception.
	 *
	 * @param msg       the message
	 * @param code      the code
	 * @param isJsonStr the is JSON string
	 */
	public CityNotFoundException(String msg, int code, boolean isJsonStr) {
		super(msg);
		this.message = isJsonStr ? extractErrorMessage(msg) : msg;
		this.statusCode = code;
	}

	/**
	 * Extract error message.
	 *
	 * @param errMsg the error message
	 * @return the string
	 */
	public String extractErrorMessage(String errMsg) {
		var mapper = new ObjectMapper();
		try {
			var readValue = mapper.readValue(errMsg, Map.class);
			return (String) readValue.get("message");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return errMsg;
		}
	}

}