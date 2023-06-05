package br.edu.toledoprudente.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests((requests) -> requests.requestMatchers("/login").permitAll()
						.requestMatchers("/img/**").permitAll().requestMatchers("/css/**").permitAll()
						.requestMatchers("/cadastro").permitAll().requestMatchers("/funcionario/cadastrar").permitAll()
						.anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll()
						.failureUrl("/login-error"))
				.logout((logout) -> logout.invalidateHttpSession(true).clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
						.permitAll());

		return http.build();
	}

}
