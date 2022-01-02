package com.calculator.incometax.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/incometax")
public class TestController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(value = "/test")
	public String test() {
		return "Test Success";
	}
}