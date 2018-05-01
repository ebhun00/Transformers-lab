package com.titan.Transformerslab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ImportResource("classpath:si-marshallers.xml")
@Slf4j

public class TransformersLabApplication {

	public static void main(String[] args) {
		log.info("Application starting");
		SpringApplication.run(TransformersLabApplication.class, args);
	}

	ApplicationContext  context = new ClassPathXmlApplicationContext("si-marshallers.xml");
}