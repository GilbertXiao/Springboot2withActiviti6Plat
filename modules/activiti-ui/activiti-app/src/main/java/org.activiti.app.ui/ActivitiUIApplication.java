package org.activiti.app.ui;

import org.activiti.app.conf.ApplicationConfiguration;
import org.activiti.app.servlet.ApiDispatcherServletConfiguration;
import org.activiti.app.servlet.AppDispatcherServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @program: ActivitiNew2019
 * @description: springboot entrance
 * @author: GilbertXiao
 * @create: 2019-03-17 23:39
 **/
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@Import({ApplicationConfiguration.class})
public class ActivitiUIApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(ActivitiUIApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ActivitiUIApplication.class);
    }

    @Bean
    public ServletRegistrationBean apiDispatcher(){
        DispatcherServlet api = new DispatcherServlet();
        api.setContextClass(AnnotationConfigWebApplicationContext.class);
        api.setContextConfigLocation(ApiDispatcherServletConfiguration.class.getName());
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(api);
        registrationBean.addUrlMappings("/api/*");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        registrationBean.setName("api");

        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean appDispatcher(){
        DispatcherServlet app = new DispatcherServlet();
        app.setContextClass(AnnotationConfigWebApplicationContext.class);
        app.setContextConfigLocation(AppDispatcherServletConfiguration.class.getName());
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(app);
        registrationBean.addUrlMappings("/app/*");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        registrationBean.setName("app");

        return registrationBean;
    }
}

