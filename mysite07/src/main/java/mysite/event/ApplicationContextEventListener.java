package mysite.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;
import mysite.aspect.MeasureExecutionTimeAspect;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Slf4j
public class ApplicationContextEventListener {
	@Autowired
	private ApplicationContext applicationContext;

	@EventListener({ ContextRefreshedEvent.class })
	public void handlerContextRefreshedEvent() {
		log.info("-- Context Refreshed Event Received --");

		SiteService siteService = applicationContext.getBean(SiteService.class);
		SiteVo vo = siteService.getSite();

		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("title", vo.getTitle());
		propertyValues.add("profile", vo.getProfile());
		propertyValues.add("welcome", vo.getWelcome());
		propertyValues.add("description", vo.getDescription());

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SiteVo.class);
		beanDefinition.setPropertyValues(propertyValues);
		
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition("site", beanDefinition);
	}
}
