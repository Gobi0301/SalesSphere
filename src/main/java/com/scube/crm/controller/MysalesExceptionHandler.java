package com.scube.crm.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 
@ControllerAdvice
public class MysalesExceptionHandler {
     
    @ExceptionHandler(value = NullPointerException.class)
    public String nullPointerHandler(Model theModel) {
        theModel.addAttribute("err", "NullPointerException");
        return "error";
    }
     
    @ExceptionHandler(value = Exception.class)
    public String AnyOtherHandler(Exception ex) {
    	ex.printStackTrace();
        return "error";
    }
     
}