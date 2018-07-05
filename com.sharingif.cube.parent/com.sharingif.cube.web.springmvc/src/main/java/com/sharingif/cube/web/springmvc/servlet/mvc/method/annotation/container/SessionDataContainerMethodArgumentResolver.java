package com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sharingif.cube.core.handler.bind.annotation.DataContainer;

/**
 * 存储session
 * 2017/5/23 下午6:05
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SessionDataContainerMethodArgumentResolver implements DataContainerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(Class<?> parameterType) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        DataContainer dataContainer = parameter.getParameterAnnotation(DataContainer.class);

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession();
        Object sessionData = session.getAttribute(dataContainer.value());
        if(null == sessionData) {

            if(parameter.getParameterType().isAssignableFrom(List.class)) {
                sessionData = new ArrayList<Object>();
                session.setAttribute(dataContainer.value(),sessionData);
                return sessionData;
            }

            if(parameter.getParameterType().isAssignableFrom(Map.class)) {
                sessionData = new HashMap<Object,Object>();
                session.setAttribute(dataContainer.value(),sessionData);
                return sessionData;
            }

            sessionData = parameter.getParameterType().newInstance();
            session.setAttribute(dataContainer.value(),sessionData);
            return sessionData;

        }

        return sessionData;
    }

}
