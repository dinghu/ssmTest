package com.fc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
@Controller
@RequestMapping("/")
public class ExceptionController {

    //	 @ExceptionHandler({ Exception.class })
    @RequestMapping("/exceptionMine")
    public ModelAndView handlerException(Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("error", "123456788");
//        System.out.println("handlerException");
//        model.addAttribute("error", "123456788");
        return mv;
    }

}
