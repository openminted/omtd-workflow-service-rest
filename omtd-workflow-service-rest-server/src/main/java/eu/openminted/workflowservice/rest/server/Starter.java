package eu.openminted.workflowservice.rest.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;



public class Starter {
	
	private final static Logger log = LoggerFactory.getLogger(Starter.class);
	
	public static void main(String[] args) {		
		log.info("Starting " + WorkflowServiceController.class.getName());
				
		
		// Run app within a Spring Context.
		SpringApplication springApplication = new SpringApplication(ApplicationRunner.class);		
		springApplication.run();
	}
}
