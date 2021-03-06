package eu.openminted.workflowservice.rest.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.MetadataHeaderInfo;
import eu.openminted.registry.domain.MetadataIdentifier;
import eu.openminted.workflow.api.ExecutionStatus;
import eu.openminted.workflow.api.WorkflowJob;
import eu.openminted.workflow.api.WorkflowService;
import eu.openminted.workflowservice.rest.common.Utils;
import eu.openminted.workflowservice.rest.common.WorkFlowREST;

@Controller
public class WorkflowServiceController {

	private static final Logger log = LoggerFactory.getLogger(WorkflowServiceController.class);
	
    private final WorkflowService workflowService;
    private ObjectMapper objectMapper;
    
    /**
     * Constructor.
     */
    @Autowired
    public WorkflowServiceController(WorkflowService workflowService) {
       this.workflowService = workflowService;
       objectMapper= new ObjectMapper();
    }    

    @RequestMapping(value=WorkFlowREST.executeJob, method=RequestMethod.POST)
    @ResponseBody
    public String executeJob(@RequestParam(WorkFlowREST.workflow) String workflow, @RequestParam(WorkFlowREST.corpusId) String corpusId, @RequestParam(WorkFlowREST.subArchive) String subArchive){    	
    	String ret = null;
    	
    	try{
    	   	Component workflowMetadata = objectMapper.readValue(workflow, Component.class);
        	log.info("wid:" + workflowMetadata.getMetadataHeaderInfo().getMetadataRecordIdentifier().getValue() + " corpusId:" + corpusId + " subArchive: " + subArchive);
	    	WorkflowJob workflowJob = new WorkflowJob(workflowMetadata, corpusId, subArchive);
	    	log.info("execute->");
	    	ret = workflowService.execute(workflowJob);
	    	return ret;
    	}catch (Exception e){
    		return ret;
    	}   	
    }

/*    
    @RequestMapping(value=WorkFlowREST.executeJob, method=RequestMethod.POST)
    @ResponseBody
    public String executeJob(@RequestParam(WorkFlowREST.workflow) String workflow, @RequestParam(WorkFlowREST.corpusId) String corpusId, @RequestParam(WorkFlowREST.subArchive) String subArchive){    	
    	//log.info("wid:" + workflow.getMetadataHeaderInfo().getMetadataRecordIdentifier().getValue() + " corpusId:" + corpusId + " subArchive: " + subArchive);
    	String ret = null;
    	
    	try{
			Component workflowC = Utils.createComponentMetadata(workflow);
	    	WorkflowJob workflowJob = new WorkflowJob(workflowC, corpusId, subArchive);
	    	log.info("execute->");
	    	ret = workflowService.execute(workflowJob);
	    	return ret;
    	}catch (Exception e){
    		return ret;
    	}   	
    }
  */
    
    @RequestMapping(value=WorkFlowREST.getStatus, method=RequestMethod.POST,  produces = "application/json")
    @ResponseBody
    public ExecutionStatus getStatus(@RequestParam(WorkFlowREST.jobID) String jobID){
    	
    	log.info("jobID:" + jobID);
    	    	
    	try{
    		ExecutionStatus status =  workflowService.getExecutionStatus(jobID);
    		return status;
    	}catch (Exception e){
    		return null;
    	}
    	
    }
    
    @RequestMapping(value=WorkFlowREST.pauseJob, method=RequestMethod.POST,  produces = "application/json")
    @ResponseBody
    public void pauseJob(@RequestParam(WorkFlowREST.workflowExecutionId) String weid){
    	
    	log.info("workflowExecutionId:" + weid);
    	    	
    	try{
    		workflowService.pause(weid);
    		//return status;
    	}catch (Exception e){
    		//return null;
    	}
    }
    
    @RequestMapping(value=WorkFlowREST.cancelJob, method=RequestMethod.POST,  produces = "application/json")
    @ResponseBody
    public void cancelJob(@RequestParam(WorkFlowREST.workflowExecutionId) String weid){
    	
    	log.info("workflowExecutionId:" + weid);
    	    	
    	try{
    		workflowService.cancel(weid);
    		//return status;
    	}catch (Exception e){
    		//return null;
    	}
    }
}
