package com.fc.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fc.util.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称 : SgccExceptionResolver. <br>
 * 功能描述 : 全局异常拦截器，可在此做异常信息的判断及输出. <br>
 * <p>
 * 创建人： Administrator <br>
 * 创建时间 : 2017年7月3日 下午15:12:36. <br>
 * </p>
 * 修改历史:  <br>
 * 修改人  修改日期  修改描述<br>
 * -----------------------------------<br>
 */
@Component
public class customException2Resolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        CustomException customException;
        if (HttpUtils.isAjaxRequest(request)) {
            String msg = null;
            if (ex instanceof CustomException) {
                customException = (CustomException) ex;
            } else {
                customException = new CustomException("未知错误");
            }
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("error", customException.getMessage());
                JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(map));
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(itemJSONObj);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("error/error");
            mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
            return mv;
        }
    }

}
