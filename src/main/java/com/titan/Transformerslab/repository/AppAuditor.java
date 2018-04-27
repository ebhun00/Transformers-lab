package com.titan.Transformerslab.repository;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component(value="auditor")
public class AppAuditor implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		return "Transformer"; 
	}

}