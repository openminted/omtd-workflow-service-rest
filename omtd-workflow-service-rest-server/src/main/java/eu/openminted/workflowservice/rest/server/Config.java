package eu.openminted.workflowservice.rest.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import eu.openminted.messageservice.connector.MessageServiceConnector;
import eu.openminted.workflow.api.WorkflowService;
import eu.openminted.workflow.service.WorkflowServiceImpl2;

@Configuration
public class Config {

	@Autowired
	private Environment env;
	
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
	public MessageServiceConnector getJMSService(){
		return new MessageServiceConnector(env.getProperty("jms.host"));
	}
}
