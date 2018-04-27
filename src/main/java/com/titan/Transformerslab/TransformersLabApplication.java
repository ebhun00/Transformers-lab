package com.titan.Transformerslab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
// @EnableWebMvc
public class TransformersLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformersLabApplication.class, args);
	}

	ApplicationContext  context = new ClassPathXmlApplicationContext("si-marshallers.xml");

	/*
	 * public static final String INBOUND_CHANNEL = "inbound-channel";
	 * 
	 * 
	 * @Bean(name = INBOUND_CHANNEL) public MessageChannel
	 * inboundFilePollingChannel() { return MessageChannels.direct().get(); }
	 */
}
// https://docs.spring.io/spring-integration/reference/html/files.html
// https://github.com/iainporter/spring-file-poller
// http://porterhead.blogspot.co.uk/2016/07/file-polling-using-spring-integration.html
// https://xpadro.com/2016/07/spring-integration-polling-file-creation-and-modification.html
// https://examples.javacodegeeks.com/enterprise-java/spring/spring-integration-poller-example/