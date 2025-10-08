package br.com.gregoriohd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.gregoriohd.service.DBService;

@Configuration
@Profile("dev")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public DBService instanciaDB() {
		this.dbService.instanciaDBService();
		return this.dbService;
	}

}
