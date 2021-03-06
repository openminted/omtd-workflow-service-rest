package eu.omtd.workflow.service.rest.examples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import eu.openminted.registry.domain.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.openminted.store.restclient.StoreRESTClient;
import eu.openminted.workflow.api.ExecutionStatus;
import eu.openminted.workflowservice.rest.client.WorkflowServiceClient;

/**
 * @author galanisd
 *
 */
public class WorkflowExecScenario {

	private String archiveIDForProcessing;
	private boolean downloadInput;

	private static final Logger log = LoggerFactory.getLogger(WorkflowExecScenario.class);

	// DG
	public static String uploadDataToStoreArchive(String endpoint, String folderWithPDFs) {
		StoreRESTClient store = new StoreRESTClient(endpoint);

		String archiveID = store.createArchive().getResponse();
		log.info("Create Archive:" + archiveID);

		String subArchiveID = store.createSubArchive(archiveID, "fulltext").getResponse();

		File folderWithPDFsHandler = new File(folderWithPDFs);
		File[] PDFs = folderWithPDFsHandler.listFiles();
		for (int i = 0; i < PDFs.length; i++) {
			store.storeFile(PDFs[i], subArchiveID, PDFs[i].getName());
		}

		store.finalizeArchive(archiveID);

		return archiveID;
	}

	// Mark G.
	private static String createAndUploadArchive(StoreRESTClient storeClient, Path archiveData, String subarchiveForData) throws IOException {
		String archiveID = storeClient.createArchive().getResponse();
		String annotationsFolderId = storeClient.createSubArchive(archiveID, subarchiveForData).getResponse();

		Files.walk(archiveData).filter(path -> !Files.isDirectory(path)).forEach(path -> {
			storeClient.storeFile(path.toFile(), annotationsFolderId, path.getFileName().toString());
		});

		storeClient.finalizeArchive(archiveID);

		return archiveID;
	}

	public static boolean isCompleted(String status) {
		// boolean completed =
		// status.equalsIgnoreCase(ExecutionStatus.Status.FINISHED.toString())
		// ||
		// status.equalsIgnoreCase(ExecutionStatus.Status.FAILED.toString()) ||
		// status.equalsIgnoreCase(ExecutionStatus.Status.CANCELED.toString());

		boolean completed = status.contains(ExecutionStatus.Status.FINISHED.toString())
				|| status.contains(ExecutionStatus.Status.FAILED.toString())
				|| status.contains(ExecutionStatus.Status.CANCELED.toString());

		return completed;
	}

	public static String getResultingCorpusId(String status, StoreRESTClient store) {
		if (status.contains(ExecutionStatus.Status.FINISHED.toString())) {
			System.out.println("status:" + status);
			
			String anchor = "corpusID";
			
			int srt = status.lastIndexOf(anchor);
			int end = status.indexOf(",", srt + anchor.length() );
			if(end == -1){
				end = status.indexOf("}", srt + anchor.length() );
			}
			
			String resultCorpusId = status.substring(srt + anchor.length() + 3, end - 1);

			log.info("resulting corpus:" + resultCorpusId);
			return resultCorpusId;
		}

		return null;
	}

	// -- -- ---
	// -- -- ---
	// -- -- ---

	public String getArchiveIDForProcessing() {
		return archiveIDForProcessing;
	}

	public boolean isDownloadInput() {
		return downloadInput;
	}

	public void setDownloadInput(boolean downloadInput) {
		this.downloadInput = downloadInput;
	}

	public void setArchiveIDForProcessing(String archiveIDForProcessing) {
		this.archiveIDForProcessing = archiveIDForProcessing;
	}

	public void runScenario(String storeEndpoint, String workflowEndpoint, String inFolder, String wid,
			String downloadPath, String archiveID, String subArchive) throws Exception {

		if (archiveID == null) {
			uploadDataToStore(inFolder, downloadPath, storeEndpoint, subArchive);
		}else{
			this.archiveIDForProcessing = archiveID;
		}

		executeAndGetResult(archiveIDForProcessing, storeEndpoint, workflowEndpoint, wid, downloadPath, subArchive);

	}

	private void uploadDataToStore(String inFolder, String downloadPath, String storeEndpoint, String subArchive) throws Exception {
		// DG
		// String archiveID = uploadDataToStoreArchive(storeEndpoint,
		// folderWithPDFs);
		// Mark
		Path archiveData = Paths.get(inFolder);
		StoreRESTClient store = new StoreRESTClient(storeEndpoint);
		String archiveID = createAndUploadArchive(store, archiveData, subArchive);
		log.info("Data uploaded to STORE " + storeEndpoint + " archiveID:" + archiveID);

		if (this.downloadInput && downloadPath != null) {
			store.downloadArchive(archiveID, downloadPath + "input.zip");
			log.info("Input data downloaded to " + downloadPath);
		}

		this.archiveIDForProcessing = archiveID;
	}

	private void executeAndGetResult(String archiveID, String storeEndpoint, String workflowEndpoint, String wid,
			String downloadPath, String subArchive) throws Exception {
		StoreRESTClient store = new StoreRESTClient(storeEndpoint);
		
		long start = System.currentTimeMillis();
		WorkflowServiceClient client = new WorkflowServiceClient(workflowEndpoint);

		log.info("Calling Workflow service");
		String jobID = client.executeJob(wid, archiveID, subArchive);
		log.info("jobID:" + jobID);

		String status = client.getStatus(jobID);
		// status.
		log.info("Status:" + status);

		while (!isCompleted(status)) {
			Thread.currentThread().sleep(5000);
			
			try{
				status = client.getStatus(jobID);
				log.info("status:" + status);
			}catch(Exception e){
				log.info("Error on getting status");
			}
		}
		long end = System.currentTimeMillis();
		log.info("workflow service processing time:" + ((end-start)/1000) + " sec");
		
		String resultCorpusId = getResultingCorpusId(status, store);

		if (resultCorpusId != null
				&& store.archiveExists(resultCorpusId).getResponse().equalsIgnoreCase(Boolean.TRUE.toString())) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss");
			LocalDateTime currentTime = LocalDateTime.now();
			
			if(downloadPath !=null){
				store.downloadArchive(resultCorpusId,
						downloadPath + "_WF_" + wid + "output_" + currentTime.format(formatter) + ".zip");
				log.info("downloading result ... " + downloadPath);
			}
		} else {
			log.info("resultCorpusId " + resultCorpusId + " does not exist.. NULL");
		}
		
		
		try{// Delete resulting corpus ID to save space.
			if(resultCorpusId != null){
				boolean deleteResult = store.deleteArchive(resultCorpusId).getResponse().equalsIgnoreCase(Boolean.TRUE.toString());
				log.info(" Delete archive " + resultCorpusId + " "  +  deleteResult);
			}else{
				log.info("resultCorpusId: " + resultCorpusId + " cannot be deleted");
			}
		}catch(Exception e){
			log.error("ERROR ON DELETING: " + resultCorpusId);
		}
	}
}
