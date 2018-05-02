package com.titan.Transformerslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PropertySource("classpath:/flags.properties")
public class EnvironmentPropsController {

	@Autowired
	private Environment env;

	@GetMapping("/all-props")
	public String getProperties() {
		return env.getProperty("xmlApproach");
	}
	
	
	@PostMapping("/all-props/key/{updateKey}")
	public String getProperties(@PathVariable("updateKey") String updateKey) {
		
		return env.getProperty(updateKey);
	}

}
