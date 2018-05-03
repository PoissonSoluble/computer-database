package com.excilys.cdb.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle(NoHandlerFoundException exception) {
        ModelAndView model = new ModelAndView("404");
        model.addObject("exception", exception);
        return model;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handle(RuntimeException exception) {
        ModelAndView model = new ModelAndView("500");
        model.addObject("exception", exception);
        return model;
    }

}