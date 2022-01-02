package com.calculator.incometax.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "url")
public class UrlProperties {
	
	private String login;
	private String loginProcessing;
	private String loginFailure;
	private String loginSuccess;
	private String logoutSuccess;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getLoginProcessing() {
		return loginProcessing;
	}
	public void setLoginProcessing(String loginProcessing) {
		this.loginProcessing = loginProcessing;
	}
	public String getLoginFailure() {
		return loginFailure;
	}
	public void setLoginFailure(String loginFailure) {
		this.loginFailure = loginFailure;
	}
	public String getLoginSuccess() {
		return loginSuccess;
	}
	public void setLoginSuccess(String loginSuccess) {
		this.loginSuccess = loginSuccess;
	}
	public String getLogoutSuccess() {
		return logoutSuccess;
	}
	public void setLogoutSuccess(String logoutSuccess) {
		this.logoutSuccess = logoutSuccess;
	}
}
