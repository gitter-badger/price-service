package ch.helsana.web.priceservice.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by marcelwidmer on 21/03/16.
 */
@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    /**
     * The following Spring Configuration declares the servlet wrapper for the H2 database console and maps it to the path of /console.
     *
     * @return a h2 WebServlet
     */
    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }


}