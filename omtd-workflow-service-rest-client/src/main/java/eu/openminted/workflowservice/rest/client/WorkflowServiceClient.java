package eu.openminted.workflowservice.rest.client;

import eu.openminted.registry.domain.Component;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.openminted.workflow.api.ExecutionStatus;
import eu.openminted.workflowservice.rest.common.Utils;
import eu.openminted.workflowservice.rest.common.WorkFlowREST;

public class WorkflowServiceClient {

	private static final Logger log = LoggerFactory.getLogger(WorkflowServiceClient.class);

	private RestTemplate restTemplate;
	private String endpoint;
	private ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * Constructor.
	 * @param endpoint
	 */
	public WorkflowServiceClient(String endpoint) {		
		this.endpoint = endpoint;
		this.restTemplate = new RestTemplate();	
		objectMapper = new ObjectMapper();
		log.info(WorkflowServiceClient.class.getName());
		//init();
	}
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String executeJob(String wid, String corpusId, String subArchive) {
		Component workflowMetadata = Utils.createComponentMetadata(wid);
		
		System.out.println("corpusId:" + corpusId);
		
		String json = "";
		try{
			json = objectMapper.writeValueAsString(workflowMetadata);
		}catch(Exception e){
			
		}
		
		// Create params.
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();	
		params.add(WorkFlowREST.workflow, json);
		params.add(WorkFlowREST.subArchive, subArchive);
		params.add(WorkFlowREST.corpusId, corpusId);
		
		return post(destination(endpoint, WorkFlowREST.executeJob), params);
	}
	
	public String getStatus(String jobID) {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add(WorkFlowREST.jobID, jobID);		
		
		return post(destination(endpoint, WorkFlowREST.getStatus), params);
	}
	
	private String post(String serviceEndpoint, MultiValueMap<String, Object> params){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.APPLICATION_JSON);
		//System.out.println("CT: " + headers.getContentType());
		
		//Iterator<org.springframework.http.converter.HttpMessageConverter<?>> it = restTemplate.getMessageConverters().iterator();
		//List<HttpMessageConverter<?>> mc = restTemplate.getMessageConverters();
	    //mc.add(new MappingJackson2HttpMessageConverter());
	    //restTemplate.setMessageConverters(mc);
		//while(it.hasNext())
		//System.out.println(it.next().toString());
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
		ResponseEntity<String> st = restTemplate.postForEntity(serviceEndpoint, requestEntity, String.class);
		
		return st.getBody();
	}
	
	private ExecutionStatus postStatus(String serviceEndpoint, MultiValueMap<String, Object> map){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);	
		ResponseEntity<ExecutionStatus> st = restTemplate.postForEntity(serviceEndpoint, requestEntity, ExecutionStatus.class);
		return st.getBody();
	}
	private String destination(String endpoint, String service){
		return endpoint + service;
	}
}
