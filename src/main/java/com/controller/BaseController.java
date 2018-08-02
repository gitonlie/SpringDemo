package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * 全局异常处理器
 * @author Administrator
 *
 */
@ControllerAdvice
public class BaseController {
	private Logger logger = LoggerFactory.getLogger(BaseController.class);
	@ExceptionHandler(Exception.class)
	public void exp(Exception e){		
		logger.error(e.getMessage(),e);
	}
}
