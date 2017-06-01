package org.omtd.workflow.service.rest.examples;

import java.io.File;

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
    
    // Main
    // ---
    // ---
    public static void main( String[] args ){
    	
    	String storeEndpoint = "http://localhost:8080/";
    	String folderWithPDFs = "C:/Users/galanisd/Desktop/Dimitris/EclipseWorkspaces/ILSPMars/omtd-simple-workflows/testInput/";
    	String archiveID = uploadDataToStoreArchive(storeEndpoint, folderWithPDFs);
    	
    	WorkflowServiceClient client = new WorkflowServiceClient("http://localhost:8881/");
    	String id = client.executeJob("DGTest1", archiveID);
    	log.info("id:" + id);
    }
}
