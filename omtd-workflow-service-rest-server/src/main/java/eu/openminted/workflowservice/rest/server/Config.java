package eu.openminted.workflowservice.rest.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import eu.openminted.registry.core.jms.service.JMSService;
import eu.openminted.workflow.api.WorkflowService;
import eu.openminted.workflow.service.WorkflowServiceImpl2;

@Configuration
public class Config {

	@Bean
	public WorkflowService getWorkflowService(){	
		return new WorkflowServiceImpl2();
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }
	
	@Bean
	public JMSService getJMSService(){
		return new JMSService();
	}
}
