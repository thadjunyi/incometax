package com.calculator.incometax.cache;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import com.calculator.incometax.security.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestCache extends HttpSessionRequestCache {

	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
		if (!SecurityUtils.isFrameworkInternalRequest(request)) {
			super.saveRequest(request, response); 
		}
	}
}