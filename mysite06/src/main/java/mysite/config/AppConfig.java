package mysite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
//@Import({ DBConfig.class, MyBatisConfig.class, SecurityConfig.class })
//@ComponentScan(basePackages = { "mysite.service", "mysite.repository", "mysite.aspect" })
public class AppConfig {

}
