package eu.omtd.workflow.service.rest.examples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private static String uploadArchive(StoreRESTClient storeClient, Path archiveData) throws IOException {
		String archiveID = storeClient.createArchive().getResponse();
		String annotationsFolderId = storeClient.createSubArchive(archiveID, "fulltext").getResponse();

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
			int srt = status.lastIndexOf(":");
			// int end = status.lastIndexOf("}", srt + 1);
			String resultCorpusId = status.substring(srt + 2, status.length() - 2);

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
			String downloadPath, String archiveID) throws Exception {

		if (archiveID == null) {
			uploadDataToStore(inFolder, downloadPath, storeEndpoint);
		}

		executeAndGetResult(archiveIDForProcessing, storeEndpoint, workflowEndpoint, wid, downloadPath);

	}

	private void uploadDataToStore(String inFolder, String downloadPath, String storeEndpoint) throws Exception {
		// DG
		// String archiveID = uploadDataToStoreArchive(storeEndpoint,
		// folderWithPDFs);
		// Mark
		Path archiveData = Paths.get(inFolder);
		StoreRESTClient store = new StoreRESTClient(storeEndpoint);
		String archiveID = uploadArchive(store, archiveData);
		log.info("Data uploaded to STORE " + storeEndpoint + " " + archiveID);

		if (this.downloadInput && downloadPath != null) {
			store.downloadArchive(archiveID, downloadPath + "input.zip");
			log.info("Input data downloaded to " + downloadPath);
		}

		this.archiveIDForProcessing = archiveID;
	}

	private void executeAndGetResult(String archiveID, String storeEndpoint, String workflowEndpoint, String wid,
			String downloadPath) throws Exception {
		StoreRESTClient store = new StoreRESTClient(storeEndpoint);
		
		long start = System.currentTimeMillis();
		WorkflowServiceClient client = new WorkflowServiceClient(workflowEndpoint);

		log.info("Calling Workflow service");
		String jobID = client.executeJob(wid, archiveID);
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
	}
}
