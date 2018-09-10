package eu.omtd.workflow.service.rest.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class WorkflowExecScenarioMain implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(WorkflowExecScenarioMain.class);
	
	@Override
	public void run(String... args) throws Exception {
		WorkflowExecScenario ec = new WorkflowExecScenario();
		
		String storeEndpoint = args[0];
		String workflowEndpoint = args[1];  
		String inFolder = args[2];
		String inArchive = args[3];
		String wid = args[4];
		
		String downloadPath = null;
		if(args.length == 6){
			downloadPath = args[5];
		}
		
		if(inFolder.equalsIgnoreCase("none")){
			inFolder = null;
		}
		
		if(inArchive.equalsIgnoreCase("none")){
			inArchive = null;
		}		
		
		log.info("storeEndpoint:" + storeEndpoint);
		log.info("workflowEndpoint:" + workflowEndpoint);
		log.info("inFolder:" + inFolder);
		log.info("inArchive:" + inArchive);
		log.info("wid:" + wid);
		
		log.info("\n\n\n Starting Scenario");
		
		log.info("\n\n\n Starting Scenario");
		if(args.length == 6 || args.length == 5){
			log.info("\n\n\n Starting Scenario");
			ec.runScenario(storeEndpoint, workflowEndpoint, inFolder, wid, downloadPath, inArchive);
		}else{
			log.info("Check args");
		}
	}
	
	// == === ==
	public static void main(String args[]) {
		log.info("...");
		SpringApplication app = new SpringApplication(WorkflowExecScenarioMain.class);
		//app.setBannerMode(Banner.Mode.OFF);
		app.setWebEnvironment(false);
		app.run(args);
		log.info("DONE!");
	}
}
