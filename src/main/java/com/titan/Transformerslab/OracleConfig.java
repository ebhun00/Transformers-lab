package com.titan.Transformerslab;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import lombok.Setter;
import oracle.jdbc.pool.OracleDataSource;

@Configuration
@ConfigurationProperties("oracle")
public class OracleConfig {

	@Setter
	private Map<String,String> inputs = new HashMap<String,String>();
	
	@NotNull
	@Setter
	private String username;

	@NotNull
	@Setter
	private String password;

	@NotNull
	@Setter
	private String url;

	@Bean
	public DataSource dataSource() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setURL(url);
		dataSource.setImplicitCachingEnabled(true);
		dataSource.setFastConnectionFailoverEnabled(true);
		return dataSource;
	}
	
	@Bean
	public NamedParameterJdbcTemplate getNamedParamJDBCTemplate() throws SQLException {
		return new NamedParameterJdbcTemplate(dataSource());
	};
	
	@Bean
	public JdbcTemplate getJdbcTemplate() throws SQLException {
		return new JdbcTemplate(dataSource());
	}
	
}
