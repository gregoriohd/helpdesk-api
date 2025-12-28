package br.com.gregoriohd.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.gregoriohd.security.JWTAuthenticationFilter;
import br.com.gregoriohd.security.JWTAuthorizationFilter;
import br.com.gregoriohd.security.JWTUtil;
import br.com.gregoriohd.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImpl detailsServiceImpl;

	//ponto de partida do spring security monitora os endpoints e filtras a solicitacoes
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, 
			AuthenticationManager authenticationManager) throws Exception {
		// http.cors().and().csrf().disable();
		//libera o frame de apresentacao do h2 qaundo o perfil de test estiver ativo;
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers(header -> header.frameOptions(frame -> frame.disable()));
		}

		http.cors(c -> c.configurationSource(corsConfiguration()));
		//csrf desabilitado, nao trabalharemos com a sessao do usuario. 
		http.csrf(csrf -> csrf.disable());
		//assegurado que a sessao de usuario nao vai ser criada
		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.authorizeHttpRequests(
						auth -> auth.requestMatchers("/login/**","/swagger-ui/**","/h2-console/**")
						.permitAll().anyRequest().authenticated());
		
		http.addFilterAt(new JWTAuthenticationFilter(authenticationManager, jwtUtil), 
				UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(
				new JWTAuthorizationFilter(authenticationManager, jwtUtil, detailsServiceImpl)
				,UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	//configuracao cors necessaria para permitir a comunicacao de app/servicos fora da api
	@Bean
	CorsConfigurationSource corsConfiguration() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays
				.asList("POST", "PUT", "GET", "DELETE", "OPTIONS", "PATCH"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// permitimos /** comunicacao de todos as fontes
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
	
	//configuracao para criptografar
	@Bean 
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}

}
