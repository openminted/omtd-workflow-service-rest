package eu.openminted.workflowservice.rest.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.MetadataHeaderInfo;
import eu.openminted.registry.domain.MetadataIdentifier;
import eu.openminted.workflow.api.ExecutionStatus;
import eu.openminted.workflow.api.WorkflowJob;
import eu.openminted.workflow.api.WorkflowService;
import eu.openminted.workflowservice.rest.common.WorkFlowREST;

@Controller
public class WorkflowServiceController {

	private static final Logger log = LoggerFactory.getLogger(WorkflowServiceController.class);
	
    private final WorkflowService workflowService;

    /**
     * Constructor.
     */
    @Autowired
    public WorkflowServiceController(WorkflowService workflowService) {
       this.workflowService = workflowService;
    }    
    
    @RequestMapping(value=WorkFlowREST.executeJob, method=RequestMethod.POST)
    @ResponseBody
    public String executeJob(@RequestParam(WorkFlowREST.workflowId) String wid, @RequestParam(WorkFlowREST.corpusId) String corpusId){
    	
    	log.info("wid:" + wid + " corpusId:" + corpusId);
    	String ret = null;
    	
    	try{
			Component workflow = Utils.createComponent(wid);
						
	    	WorkflowJob workflowJob = new WorkflowJob(workflow, corpusId);
	    	log.info("execute->");
	    	ret = workflowService.execute(workflowJob);
	    	return ret;
    	}catch (Exception e){
    		return ret;
    	}   	
    }
    
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
    
    
}
