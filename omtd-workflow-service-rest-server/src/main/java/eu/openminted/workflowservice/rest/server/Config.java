package eu.openminted.workflowservice.rest.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import eu.openminted.messageservice.connector.MessageServicePublisher;
import eu.openminted.messageservice.connector.MessageServiceSubscriber;
import eu.openminted.workflow.api.WorkflowService;
import eu.openminted.workflow.service.WorkflowServiceImpl;
import eu.openminted.workflow.service.WorkflowServiceMessagesHandler;

@Configuration
public class Config {

	@Autowired
	private Environment env;
		
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
	public WorkflowService getWorkflowService(){	
		return new WorkflowServiceImpl(getMessageServicePublisher(), getMessageServiceSubscriber());
	}

	@Bean
	public MessageServicePublisher getMessageServicePublisher(){
		return new MessageServicePublisher(env.getProperty("messageService.host"));
	}

	@Bean
	public MessageServiceSubscriber getMessageServiceSubscriber(){
		
		MessageServiceSubscriber subscriber = new MessageServiceSubscriber(env.getProperty("messageService.host"));
		subscriber.setMessagesHandler(new WorkflowServiceMessagesHandler());
		return subscriber;
	}
	
}
