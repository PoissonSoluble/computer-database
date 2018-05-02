package com.excilys.cdb.springmvc.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext context) {
        DispatcherServlet servlet = new DispatcherServlet(context);
        servlet.setThrowExceptionIfNoHandlerFound(true);
        return servlet;
    }
    
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext context = (WebApplicationContext)super.createRootApplicationContext();
        ((ConfigurableEnvironment)context.getEnvironment()).setActiveProfiles("webmvc");
        return context;
    }
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebConfig.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}