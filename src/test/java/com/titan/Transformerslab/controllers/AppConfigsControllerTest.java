package com.titan.Transformerslab.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.titan.Transformerslab.domain.AppConfigs;
import com.titan.Transformerslab.repository.AppConfigsRepositoryImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(AppConfigsController.class)
public class AppConfigsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppConfigsRepositoryImpl appConfigsRepositoryImpl;

	@Test
	public void verify_AppConfig_saved_successfully() throws Exception {
		String key = "some_key";
		String value = "some_value";

		doNothing().doThrow(new RuntimeException()).when(appConfigsRepositoryImpl).saveAppConfig(any(AppConfigs.class));

		mockMvc.perform(post("/app-config/key/" + key + "/value/" + value).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void verify_AppConfig_saved_fail_when_wrong_url() throws Exception {
		String key = "some_key";
		String value = "some_value";

		mockMvc.perform(post("/app-config/key/" + key + "value/" + value).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void verify_getAppconfigByKey() throws Exception {
		String key = "some_key";
		String value = "some_value";

		AppConfigs config = new AppConfigs(key, value);
		when(appConfigsRepositoryImpl.findByKey(key)).thenReturn(config);

		mockMvc.perform(get("/app-config/key/" + key).contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.key").value("some_key"));
	}

	@Test
	public void verify_getAllAppconfig() throws Exception {
		String key = "some_key";
		String value = "some_value";

		AppConfigs config = new AppConfigs(key, value);
		when(appConfigsRepositoryImpl.find()).thenReturn(Arrays.asList(config));

		mockMvc.perform(get("/app-config/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
}
