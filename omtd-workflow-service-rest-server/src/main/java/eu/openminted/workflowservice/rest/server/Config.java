package eu.openminted.workflowservice.rest.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.openminted.workflow.api.WorkflowService;
import eu.openminted.workflow.service.WorkflowServiceImpl2;

@Configuration
public class Config {

	@Bean
	public WorkflowService getWorkflowService(){	
		return new WorkflowServiceImpl2();
	}
}
