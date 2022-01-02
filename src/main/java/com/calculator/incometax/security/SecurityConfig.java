package com.calculator.incometax.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.calculator.incometax.cache.RequestCache;
import com.calculator.incometax.properties.LoginProperties;
import com.calculator.incometax.properties.UrlProperties;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	LoginProperties loginProperties;
	
	@Autowired
	UrlProperties urlProperties;
	
	/**
	 * Require login to access internal pages and configure login form.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Vaadin handles CSRF internally
		http.csrf().disable()
	
	    // Register our CustomRequestCache, which saves unauthorized access attempts, so the user is redirected after login.
	    .requestCache().requestCache(new RequestCache())
	
	    // Restrict access to our application.
	    .and().authorizeRequests()
	
	    // Allow all Vaadin internal requests.
	    .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
	
	    // Allow vaadin requests by logged-in users.
//	    .antMatchers("/v1/incometax/vaadin/**").authenticated()
	    // Allow all other page request by any user
	    .anyRequest().permitAll()
	
	    // Configure the login page.
	    .and().formLogin()
	    .loginPage(urlProperties.getLogin())
	    .loginProcessingUrl(urlProperties.getLoginProcessing())
	    .defaultSuccessUrl(urlProperties.getLoginSuccess())
	    .failureUrl(urlProperties.getLoginFailure())
	
	    // Configure logout
	    .and().logout().logoutSuccessUrl(urlProperties.getLogoutSuccess());
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername(loginProperties.getUsername())
				.password("{noop}" + loginProperties.getPassword())
				.roles("USER")
				.build();
	
		return new InMemoryUserDetailsManager(user);
	}
	
	/**
	 * Allows access to static resources, bypassing Spring Security.
	 */
//	@Override
//	public void configure(WebSecurity web) {
//		web.ignoring().antMatchers(
//				// Client-side JS
//				"/VAADIN/**",
//	
//				// the standard favicon URI
//				"/favicon.ico",
//	
//				// the robots exclusion standard
//				"/robots.txt",
//	
//				// web application manifest
//				"/manifest.webmanifest",
//				"/sw.js",
//				"/offline.html",
//	
//				// icons and images
//				"/icons/**",
//				"/images/**",
//				"/styles/**",
//	
//				// (development mode) H2 debugging console
//				"/h2-console/**");
//	}
}