package com.titan.Transformerslab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titan.Transformerslab.domain.AppConfigs;
import com.titan.Transformerslab.repository.AppConfigsRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

public class AppConfigsController {

	@Autowired
	private AppConfigsRepositoryImpl appConfigsRepositoryImpl;

	@PostMapping("/app-config/key/{key}/value/{value}")
	public void updateAppconfig(@PathVariable(name = "key") String key, @PathVariable(name = "value") String value)
			throws Exception {

		log.info("Saving AppConfig for the key" + key  +"and Value : "+ value);
		AppConfigs config = new AppConfigs(key, value);

		appConfigsRepositoryImpl.saveAppConfig(config);
	}

	@GetMapping("/app-config/key/{key}")
	public AppConfigs getAppconfigByKey(@PathVariable(name = "key") String key) throws Exception {
		log.info("getting AppConfig for the key" + key);
		AppConfigs config = appConfigsRepositoryImpl.findByKey(key);
		return config;
	}
	
	@GetMapping("/app-config/")
	public List<AppConfigs> getAppconfig() throws Exception {
		log.info("Getting all appConfig details");

		List<AppConfigs> configs = appConfigsRepositoryImpl.find();
		return configs;
	}

}
