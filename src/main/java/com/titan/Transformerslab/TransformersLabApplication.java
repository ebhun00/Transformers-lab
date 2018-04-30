package com.titan.Transformerslab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@ImportResource("classpath:si-marshallers.xml")
public class TransformersLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformersLabApplication.class, args);
	}

	ApplicationContext  context = new ClassPathXmlApplicationContext("si-marshallers.xml");
}