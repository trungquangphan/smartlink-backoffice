package ch.smartlink.backoffice.master.config.web;

import ch.smartlink.backoffice.master.config.security.BOSpringSecurityDialet;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by doomphantom on 21/01/2016.
 */
@Configuration
public class ThymeleafConfig {

    @Value("${thymeleaf.cacheable}")
    private boolean thymeLeafCacheable;

    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver();
        servletContextTemplateResolver.setPrefix("/WEB-INF/views/");
        servletContextTemplateResolver.setSuffix(".html");
        servletContextTemplateResolver.setTemplateMode("HTML5");
        servletContextTemplateResolver.setCacheable(thymeLeafCacheable);
        return servletContextTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver());
        Set<IDialect> additionalDialets = new HashSet<IDialect>();
        additionalDialets.add(new LayoutDialect());
        additionalDialets.add(new BOSpringSecurityDialet());
        springTemplateEngine.setAdditionalDialects(additionalDialets);
        return springTemplateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }
}
