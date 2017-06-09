package eu.openminted.workflowservice.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import eu.openminted.workflowservice.rest.common.WorkFlowREST;

public class WorkflowServiceClient {

	private static final Logger log = LoggerFactory.getLogger(WorkflowServiceClient.class);

	private RestTemplate restTemplate;
	private String endpoint;

	/**
	 * Constructor.
	 * @param endpoint
	 */
	public WorkflowServiceClient(String endpoint) {		
		this.endpoint = endpoint;
		this.restTemplate = new RestTemplate();	
		
		//init();
	}
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String executeJob(String workflowId, String corpusId) {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add(WorkFlowREST.workflowId, workflowId);
		params.add(WorkFlowREST.corpusId, corpusId);
		
		return post(destination(endpoint, WorkFlowREST.executeJob), params);
	}
	
	public String getStatus(String jobID) {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add(WorkFlowREST.jobID, jobID);		
		
		return post(destination(endpoint, WorkFlowREST.getStatus), params);
	}
	
	private String post(String serviceEndpoint, MultiValueMap<String, Object> map){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);	
		ResponseEntity<String> st = restTemplate.postForEntity(serviceEndpoint, requestEntity, String.class);
		return st.getBody();
	}
	
	private String destination(String endpoint, String service){
		return endpoint + service;
	}
}
