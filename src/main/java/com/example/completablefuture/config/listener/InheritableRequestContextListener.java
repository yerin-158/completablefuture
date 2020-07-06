package com.example.completablefuture.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

//@Configuration
//@WebListener
@Slf4j
public class InheritableRequestContextListener extends RequestContextListener {

    private static final String REQUEST_ATTRIBUTES_ATTRIBUTE =
            InheritableRequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";

    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        log.info(" >>>>> InheritableRequestContextListener");
        if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
            throw new IllegalArgumentException(
                    "Request is not an HttpServletRequest: " + requestEvent.getServletRequest());
        }
        HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
        LocaleContextHolder.setLocale(request.getLocale());
        RequestContextHolder.setRequestAttributes(attributes, true);
        log.info(" >>>> "+attributes.toString());
    }

}
