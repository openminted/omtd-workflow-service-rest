package eu.omtd.workflow.service.rest.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleScenarioMain implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(ExampleScenarioMain.class);
	
	@Override
	public void run(String... args) throws Exception {
		ExampleScenario ec = new ExampleScenario();
		
		String storeEndpoint = args[0];
		String workflowEndpoint = args[1];  
		String inFolder = args[2];
		String wid = args[3];
		
		String downloadPath = null;
		if(args.length == 5){
			downloadPath = args[4];
		}
				
		if(args.length == 5 || args.length == 4){
			log.info("\n\n\n Starting Scenario");
			ec.runScenario(storeEndpoint, workflowEndpoint, inFolder, wid, downloadPath);
		}else{
			log.info("Check args");
		}
	}
	
	// == === ==
	public static void main(String args[]) {
		log.info("...");
		SpringApplication app = new SpringApplication(ExampleScenarioMain.class);
		//app.setBannerMode(Banner.Mode.OFF);
		app.setWebEnvironment(false);
		app.run(args);
		log.info("DONE!");
	}
}
