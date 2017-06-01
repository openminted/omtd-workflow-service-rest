package eu.openminted.workflowservice.rest.client;

public class WorkflowServiceClientTest {
	
	public static void main(String args[]){
		
		// We need the Galaxy ID.
		String workflowID = "123";

		WorkflowServiceClient client = new WorkflowServiceClient("http://localhost:8881/");
		
		client.executeJob(workflowID, "321");
	}
	
	
}
