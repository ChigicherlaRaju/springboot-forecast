package com.boot.forecast.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.forecast.constants.ForecastConstants;
import com.boot.forecast.exception.CityNotFoundException;
import com.boot.forecast.model.WeatherAverageDTO;
import com.boot.forecast.service.ForecastService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

// @formatter:off

/**
 * The Class ForecastController.
 */
@RestController
public class ForecastController {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ForecastController.class);

	/** The forecast service. */
	@Autowired
	private ForecastService forecastService;

	/**
	 * Home page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/")
	public ModelAndView homePage() {
		LOG.info("Inside ForecastController homePage() method!!!");
		return new ModelAndView("index");
	}

	/**
	 * Gets the hello world.
	 *
	 * @param name the name
	 * @return the hello world
	 */
	@GetMapping("/helloworld/{name}")
	public String getHelloWorld(@PathVariable String name) {
		LOG.info("Inside ForecastController getHelloWorld() method!!!");
		return "Hello World " + name;
	}

	/**
	 * Weather forecast average.
	 *
	 * @param city the city
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Operation(summary = "This is to fetch next 3 days Weather forecast")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Details of Next 3 days Weather Forecast", content = {@Content(mediaType = "application/json") }), })
	@GetMapping(value = "/forecast", produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView weatherForecastAverage(@RequestParam(required = true) String city) throws Exception {
		ResponseEntity<Map<String, Object>> weatherResponse = forecastService.weatherForecastAverage(city);
		Map<String, Object> responseBody = weatherResponse.getBody();
		ModelAndView mv = new ModelAndView("weather-data");
		mv.addObject("weatherDetails", (List<WeatherAverageDTO>) responseBody.get(ForecastConstants.WEATHER_AVERAGE));
		mv.addObject("weatherMsg", (String) responseBody.get(ForecastConstants.WEATHER_MESSAGE));
		return mv;
	}

	/**
	 * Gets the forecast data.
	 *
	 * @param city the city
	 * @param days the days
	 * @return the forecast data
	 * @throws Exception the exception
	 */
	@Operation(summary = "This is to fetch next 3 days Weather forecast")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Details of Next 3 days Weather Forecast", content = {@Content(mediaType = "application/json") }), })
	@GetMapping(value = "/getForecast", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getForecastData(@RequestParam(required = true) String city, @RequestParam(required = false) String days) throws Exception {
		return forecastService.getForecastInfo(city, days);
	}

	/**
	 * Handle employee not found exception.
	 *
	 * @param request the request
	 * @param ex the {@link Exception}
	 * @return the model and view
	 */
	@ExceptionHandler(CityNotFoundException.class)
	public ModelAndView handleEmployeeNotFoundException(HttpServletRequest request, Exception ex) {
		LOG.error("Requested URL: {}, Path: {}", request.getRequestURL(), request.getPathInfo());
		LOG.error("Exception Raised: {}", ex);

		ModelAndView mvc = new ModelAndView("error");
		mvc.addObject("message", ex.getMessage());
		mvc.addObject("url", request.getRequestURL());

		return mvc;
	}
}

// @formatter:on