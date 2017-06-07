package eu.openminted.workflowservice.rest.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import eu.openminted.workflow.service.WorkflowServiceImpl;
import eu.openminted.workflow.service.WorkflowServiceImpl2;

@SpringBootApplication
@ComponentScan(basePackageClasses = {WorkflowServiceController.class, Config.class})
public class ApplicationRunner {

}

