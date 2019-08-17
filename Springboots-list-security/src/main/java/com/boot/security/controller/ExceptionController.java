package com.boot.security.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

	
	@ExceptionHandler
	public ModelAndView error(Exception e) {
		ModelAndView mode=new ModelAndView();
			System.out.println("-------------------ModelAndView------------------------");
			mode.addObject("error", "66666666666666666666---"+e.getMessage());
			mode.setViewName("403");
		return mode;
		
	}
}
