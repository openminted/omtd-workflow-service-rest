package eu.openminted.workflowservice.rest.client;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.MetadataHeaderInfo;
import eu.openminted.registry.domain.MetadataIdentifier;

public class WorkflowServiceClientTest {
	
	public static void main(String args[]){
		
		// We need the Galaxy ID.
		String workflowID = "123";
		Component workflow = new Component();

		workflow.setMetadataHeaderInfo(new MetadataHeaderInfo());
		workflow.getMetadataHeaderInfo().setMetadataRecordIdentifier(new MetadataIdentifier());
		workflow.getMetadataHeaderInfo().getMetadataRecordIdentifier().setValue(workflowID);

		WorkflowServiceClient client = new WorkflowServiceClient("http://localhost:8881/");
		
		//client.executeJob(workflow, "321", "fulltext");
	}
	
	
}
