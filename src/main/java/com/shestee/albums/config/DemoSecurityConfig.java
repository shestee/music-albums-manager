package com.shestee.albums.config;

import javax.sql.DataSource;

import com.shestee.albums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
    DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring().antMatchers("/resources/**"); }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/resources/**").permitAll()
				.and()
				.formLogin().
					loginPage("/showMyLoginPage")
					.loginProcessingUrl("/authenticateTheUser")
					.successHandler(customAuthenticationSuccessHandler)
					.permitAll()
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/acces-denied");

		http.csrf().disable();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
}