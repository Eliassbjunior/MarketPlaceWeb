/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice(annotations = Controller.class)
public class MyViewControllerAdvice {
    
    @ExceptionHandler(Exception.class)
    public String errorException(Exception e, Model model){
        model.addAttribute("msgErros", e.getMessage());
        return "error";
    }
}
