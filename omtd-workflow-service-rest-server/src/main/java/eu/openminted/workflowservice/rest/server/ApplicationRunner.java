package eu.openminted.workflowservice.rest.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import eu.openminted.workflow.service.WorkflowServiceImpl;

@SpringBootApplication
@ComponentScan(basePackageClasses = {WorkflowServiceController.class, Config.class})
public class ApplicationRunner {

}

