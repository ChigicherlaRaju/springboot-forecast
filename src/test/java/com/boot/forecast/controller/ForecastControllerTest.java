package com.boot.forecast.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.boot.forecast.model.WeatherAverageDTO;
import com.boot.forecast.service.ForecastService;

//@WebMvcTest(ForecastController.class)
@SpringBootTest(classes = ForecastController.class)
@AutoConfigureMockMvc
class ForecastControllerTest {

	private static final Logger LOG = LoggerFactory.getLogger(ForecastControllerTest.class);

	@MockBean
	ForecastService forecastService;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@WithMockUser(username = "user1", password = "password", roles = {"USER"})
	public void testWeatherForecastAverage() throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		var result = new ArrayList<WeatherAverageDTO>();
		WeatherAverageDTO obj1 = new WeatherAverageDTO();
		WeatherAverageDTO obj2 = new WeatherAverageDTO();
		WeatherAverageDTO obj3 = new WeatherAverageDTO();

		result.add(obj1);
		result.add(obj2);
		result.add(obj3);

		resultMap.put("WeatherAverage", result);
		resultMap.put("WeatherMessage", "Testing");

		ResponseEntity testData = new ResponseEntity<>(resultMap, HttpStatus.OK);
		Mockito.when(forecastService.weatherForecastAverage(Mockito.anyString())).thenReturn(testData);
		mockMvc.perform(get("/forecast").param("city", "Bengaluru")).andExpect(status().isOk());
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@WithMockUser(username = "user1", password = "password", roles = {"USER"})
	public void testWeatherForecastAverage_1() throws Exception {
		LOG.info("Checking the City not found exception");
		ResponseEntity testData = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		Mockito.when(forecastService.weatherForecastAverage(Mockito.anyString())).thenReturn(testData);
		mockMvc.perform(get("/forecast")).andExpect(status().isBadRequest());
	}

}