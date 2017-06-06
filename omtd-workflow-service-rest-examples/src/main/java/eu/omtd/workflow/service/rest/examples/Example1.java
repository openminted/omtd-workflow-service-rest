package eu.omtd.workflow.service.rest.examples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.openminted.store.restclient.StoreRESTClient;
import eu.openminted.workflowservice.rest.client.WorkflowServiceClient;

/**
 * @author galanisd
 *
 */
public class Example1 {
        
	private static final Logger log = LoggerFactory.getLogger(Example1.class);
	
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
    
    // Main
    // ---
    // ---
    public static void main( String[] args ){
    	
    	try{
    		String storeEndpoint = "http://localhost:8080/";
    		String folderWithPDFs = "/home/ilsp/Desktop/DG/OMTD/omtd-simple-workflows/testInput/";
    	
    		// DG
    		//String archiveID = uploadDataToStoreArchive(storeEndpoint, folderWithPDFs);
    		//Mark
    		Path archiveData = Paths.get(folderWithPDFs);
    		String archiveID = uploadArchive(new StoreRESTClient(storeEndpoint), archiveData);
    	
    		WorkflowServiceClient client = new WorkflowServiceClient("http://localhost:8881/");
    		String id = client.executeJob("DGTest1", archiveID);
    		log.info("id:" + id);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    

    
}
