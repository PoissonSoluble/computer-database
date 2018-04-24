package com.excilys.cdb.springmvc.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(NoHandlerFoundException exception) {
        ModelAndView model = new ModelAndView("404");
        model.addObject("exception", exception);
        return model;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handle(RuntimeException exception) {
        ModelAndView model = new ModelAndView("500");
        model.addObject("exception", exception);
        return model;
    }
}