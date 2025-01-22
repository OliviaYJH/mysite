package mysite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
//@Import({ MvcConfig.class, LocaleConfig.class, FileUploadConfig.class })
//@ComponentScan({ "mysite.controller", "mysite.exception" })
public class WebConfig {

}
