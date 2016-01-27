package ch.smartlink.backoffice.master.config;

import ch.smartlink.backoffice.common.util.BeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by doomphantom on 21/01/2016.
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"ch.smartlink.backoffice", "com.smartlink"})
public class BackOfficeApplication {

    @Bean
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }

}
