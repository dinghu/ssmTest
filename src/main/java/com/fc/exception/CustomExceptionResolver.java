package com.fc.exception;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 * springmvc提供一个HandlerExceptionResolver接口
 * 只要实现该接口，并配置到spring 容器里，该类就能
 * 成为默认全局异常处理类
 * <p>
 * 全局异常处理器只有一个，配置多个也没用。
 */
//@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {
    private String ERROR_ATTRIBUTE_NAME = "error";
    private String ERROR_HTML_PAGE_NAME = "error";

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        System.out.println("resolveException");

        ModelAndView modelAndView = new ModelAndView();

        CustomException customException;
        if (e instanceof CustomException) {
            customException = (CustomException) e;
        } else if (e instanceof UnknownAccountException) {
            //用户名错误异常
//            modelAndView.addObject(ERROR_ATTRIBUTE_NAME, "没有该用户");
//            modelAndView.setViewName(ERROR_HTML_PAGE_NAME);
//            return modelAndView;
            customException = new CustomException("没有该用户");
        } else if (e instanceof IncorrectCredentialsException) {
//            System.out.println("密码错误");
//            //用户名错误异常
//            modelAndView.addObject(ERROR_ATTRIBUTE_NAME, "密码错误");
//            modelAndView.setViewName(ERROR_HTML_PAGE_NAME);
//            return modelAndView;
            customException = new CustomException("密码错误");
        } else {
            customException = new CustomException("未知错误");
        }

        //错误信息
        String message = customException.getMessage();
        System.out.println("resolveException:message:" + message);

        //错误信息传递和错误页面跳转
        modelAndView.addObject(ERROR_ATTRIBUTE_NAME, message);
        modelAndView.setViewName(ERROR_HTML_PAGE_NAME);

        return modelAndView;
    }
}
