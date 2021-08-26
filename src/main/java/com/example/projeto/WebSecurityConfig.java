package com.example.projeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private javax.sql.DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		return jdbcUserDetailsManager;
	}
	
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/list", "/painel", "/login*").authenticated().and()
				.formLogin(form -> form.loginPage("/login")
						.defaultSuccessUrl("/", true)
						.permitAll())
				.logout(logout -> {
					logout.logoutUrl("/logout")
					.logoutSuccessUrl("/");
				});
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/vendor/**",
				"/fonts/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		/*
		UserDetails user = User.builder()
				.username("joao")
				.password(encoder.encode("joao"))
				.roles("ADM")
				.build();
		*/
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(encoder);
		// .withUser(user);
		
	}

}
