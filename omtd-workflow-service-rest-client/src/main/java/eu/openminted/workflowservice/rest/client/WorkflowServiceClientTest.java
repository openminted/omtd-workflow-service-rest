package eu.openminted.workflowservice.rest.client;

public class WorkflowServiceClientTest {
	public static void main(String args[]){
		WorkflowServiceClient client = new WorkflowServiceClient("http://localhost:8888/");
		client.executeJob("123", "321");
	}
}
