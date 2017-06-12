package eu.omtd.workflow.service.rest.examples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.openminted.store.restclient.StoreRESTClient;
import eu.openminted.workflow.api.ExecutionStatus;
import eu.openminted.workflowservice.rest.client.WorkflowServiceClient;

/**
 * @author galanisd
 *
 */
public class ExampleScenario {
        
	private static final Logger log = LoggerFactory.getLogger(ExampleScenario.class);
	
	// DG
    public static String uploadDataToStoreArchive(String endpoint, String folderWithPDFs){
		StoreRESTClient store = new StoreRESTClient(endpoint);
		
		String archiveID = store.createArchive().getResponse();
		log.info("Create Archive:" + archiveID);
		
		String subArchiveID = store.createSubArchive(archiveID, "fulltext").getResponse();
		
		File folderWithPDFsHandler = new File(folderWithPDFs);
		File [] PDFs = folderWithPDFsHandler.listFiles();
		for(int i = 0; i < PDFs.length; i++){
			store.storeFile(PDFs[i], subArchiveID, PDFs[i].getName());			
		}
		
		store.finalizeArchive(archiveID);
		
		return archiveID;
	}
	
    // Mark G.
    private static String uploadArchive(StoreRESTClient storeClient, Path archiveData) throws IOException {
		String archiveID = storeClient.createArchive().getResponse();
		String annotationsFolderId = storeClient.createSubArchive(archiveID, "fulltext").getResponse();

		Files.walk(archiveData).filter(path -> !Files.isDirectory(path)).forEach(path -> {
			storeClient.storeFile(path.toFile(), annotationsFolderId, path.getFileName().toString());
		});

		storeClient.finalizeArchive(archiveID);

		return archiveID;
	}
        
    public static boolean isCompleted(String status){
		boolean completed = status.equalsIgnoreCase(ExecutionStatus.Status.FINISHED.toString()) ||
				status.equalsIgnoreCase(ExecutionStatus.Status.FAILED.toString()) ||
				status.equalsIgnoreCase(ExecutionStatus.Status.CANCELED.toString());
		
		return completed; 
    }

    public void runScenario(String storeEndpoint, String workflowEndpoint, String inFolder, String wid, String downloadPath) throws Exception{
		// DG
		//String archiveID = uploadDataToStoreArchive(storeEndpoint, folderWithPDFs);
		//Mark
		Path archiveData = Paths.get(inFolder);
		StoreRESTClient store = new StoreRESTClient(storeEndpoint);
		String archiveID = uploadArchive(store, archiveData);    	
		log.info("Data uploaded to STORE " + storeEndpoint + " " + archiveID);
		
		if(downloadPath != null){
			store.downloadArchive(archiveID, downloadPath);
			log.info("Data downloaded to " + downloadPath);
		}
		
		WorkflowServiceClient client = new WorkflowServiceClient(workflowEndpoint);    		    		
		
		String jobID = client.executeJob(wid, archiveID);
		log.info("jobID:" + jobID);
		
		String status = client.getStatus(jobID);
		log.info("Status:" + status);

		while(!isCompleted(status)){
			Thread.currentThread().sleep(5000);
			status = client.getStatus(jobID);
			log.info("status:" + status);
		}
    }
    
    // Main
    // ---
    // ---
    public static void main( String[] args ){
    	
    	try{
    		// --- Choose storeEndpoint.
    		//String storeEndpoint = "http://localhost:8080/";
    		//String storeEndpoint = "http://snf-754063.vm.okeanos.grnet.gr:8888/";
    		String storeEndpoint = "http://83.212.101.85:8090";

    		// --- Choose workflowEndpoint.
    		//String workflowEndpoint = "http://localhost:8881/";    		    		
    		String workflowEndpoint = "http://snf-754063.vm.okeanos.grnet.gr:8881/";    		
    		
    		// --- Choose input
    		//String inFolder = "/home/ilsp/Desktop/DG/OMTD/omtd-simple-workflows/testInput/";
    		String inFolder = "C:/Users/galanisd/Desktop/smallPDFs/";
    		//String inFolder = "/home/ilsp/Desktop/smallPDFs/";
    		//String inFolder = "/home/ilsp/Desktop/TextFiles/";
    		
    		// --- 
    		String downloadPath = "C:/Users/galanisd/Desktop/data.zip";
    		//String downloadPath = "/home/ilsp/Desktop/data.zip";
    		
    		// --- Select workflow
    		String wid = "DGTest1";
    		//String wid = "DGTest2NoDocker";
    		//String wid = "DGTest3";
    		//String wid = "funding-mining";
    		//String wid = "Datacite";    		
    		//String wid = "omtd workflow";

    		ExampleScenario ec = new ExampleScenario();
    		ec.runScenario(storeEndpoint, workflowEndpoint, inFolder, wid, downloadPath);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
