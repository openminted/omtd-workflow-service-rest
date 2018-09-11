package eu.omtd.workflow.service.rest.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.openminted.corpus.OMTDCorpus;

public class Example {
	
	private WorkflowExecScenario ec; 
	private static final Logger log = LoggerFactory.getLogger(Example.class);
	
	public void configureWorkflowScenarioAndRunIt(String archiveID) {
		try {

			// TO-DO !!!!!!!!!!!!
			// Move config to a properties file.
			// TO-DO !!!!!!!!!!!!
			
			// --- Choose storeEndpoint.
			// String storeEndpoint = "http://localhost:8080/";
			// String storeEndpoint =
			// "http://snf-754063.vm.okeanos.grnet.gr:8888/";
			String storeEndpoint = "http://83.212.101.85:8090/";

			// --- Choose workflowEndpoint.
			// String workflowEndpoint = "http://localhost:8881/";
			
			// Theo cluster
			//String workflowEndpoint = "http://snf-754063.vm.okeanos.grnet.gr:8881/";
			// Prod cluster: Galaxy VM
			//String workflowEndpoint = "http://83.212.72.92:8881/";
			
			// Prod cluster: worklfow-service VM
			//String workflowEndpoint = "http://83.212.72.106:8881/";
			// Test cluster:
			//String workflowEndpoint = "http://83.212.99.0:8881/";
			
			//String workflowEndpoint = "83.212.101.141:8881/";
			// beta
			//String workflowEndpoint = "http://83.212.98.33:8881/";
			// test
			//String workflowEndpoint = "http://83.212.72.105:8881/";
			
			String workflowEndpoint = "http://83.212.98.33:8881/";
			
			String inputFolder = "/home/ilsp/Desktop/OMTDProcessingExp/";
			String outputFolder = "/home/ilsp/Desktop/OMTDProcessingExp/";

			// --- Choose input
			//String dataset = "corpusMarkNameO";
			//String dataset = "corpusMark";
			String dataset = "IN_2PDFs";
			//String dataset = "xmiINWOTS";
			//String dataset = "xmiINIXA";
			//String dataset = "xmiINAK";
			//String dataset = "IN_REC";
			//String dataset = "IN_10PDFs";
			//String dataset = "docs";
			//String dataset = "docsAll";
			//String dataset = "IN_GreekTexts";
			//String dataset = "IN_TextFiles";
			// String dataset = "IN_GreekTexts20";
			// String dataset = "OMTD_Demo_Dataset1";
			 
			// String dataset = "IN_2XMIs";
			// String dataset = "IN_10XMIs";

			inputFolder = inputFolder + dataset + "/";

			// --- Select workflow`
			//String wid = "DGTest1";
			//String wid = "DGTest2NoDocker";
			//String wid = "DGTest3";
			
			//String wid = "DemoWF1Funding-mining";
			//String wid = "DemoWF2Datacite";
			//String wid = "DemoWF3SSHNER";
			//String wid = "DemoWF4Metabolites";	
			
			//String wid = "JustOmtdImport";
			//String wid = "OmtdImport";
			String wid = "omtdDKProTest00PDF2Text";
			//String wid = "omtdDKProTest01SegmWithCORENLP";
			//String wid = "omtdDKProTest02Readability";
			//String wid = "OLDomtdDKProTest02Readability";
			//String wid = "omtdDKProTest03LangIdentification";
			//String wid = "omtdDKProTest04NGramAnnotator";
			//String wid = "omtdDKProTest05SnowballStemmer";
			//String wid = "omtdDKProTest06FreqCounter";
			//String wid = "omtdDKProTest07NLP4j";
			//String wid = "omtdDKProTest08CamelCaseTok";
			//String wid = "omtdDKProTest09ICuSegmenter";
			//String wid = "omtdDKProTest10Ark";
			//String wid = "omtdDKProTest11OpenNLP";
			//String wid = "omtdDKProTest12LingPipe";
			//String wid = "omtdDKProTest13LineBasedSegmenter";
			//String wid = "omtdDKProTest14WhitespaceSegmenter";
			//String wid = "omtdDKProTest15ParagraphSplitter";
			//String wid = "omtdDKProTest16BerkeleyParser";
			//String wid = "omtdDKProTest17OpenNLP";
			//String wid = "omtdDKProTest18ClearNLP";
			//String wid = "omtdDKProTest19Writers";
			//String wid = "omtdDKProTest20TikaReader";
			//String wid = "omtdDKProTest21BreakIteratorSeg";
			//String wid = "omtdDKProTest22CORENLP";
			//String wid = "omtdDKProTest23CORENLP";
			//String wid = "omtdDKProTest24";
			//String wid = "omtdDKProTest25";
			//String wid = "omtdDKProTest26Stanford";
			//String wid = "omtdDKProTest27Mate";
			
			//String wid = "omtdGATETest00AnnieTokenizer";
			//String wid = "omtdGATETest00AnnieTokenizerWithCustomParams";
			//String wid = "omtdGATETest01AnnieSplitter";
			//String wid = "omtdGATETest02AnniePOS";

			// String wid = "TopicInference";
			// String wid = "LDA2";
			// String wid = "omtd workflow";
			
			//String wid = "Chebi";
			//String wid = "Neuro";
			//String wid = "openNLP";
			//String wid = "LangDet";
			//String wid = "IXA";
			//String wid = "GPV";
			//String wid = "fr.univnantes.termsuite.tools.TerminologyExtractorCLI";
			//String wid = "INRASegmenter";
			
			
			String subArchive = OMTDCorpus.subArchiveFullText;
			ec.runScenario(storeEndpoint, workflowEndpoint, inputFolder, wid, outputFolder, archiveID, subArchive);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exec(){
		
		ec = new WorkflowExecScenario();
		int times = 1;
		// -- NO 
		String aid = null;
		//aid = "5ce418e4-976b-48f6-bb5a-c14a0d4c8548";
		//aid="dcde4f3a-384a-44e7-ac3d-3d0f06e93363";
		//aid= "e1979041-4f96-4263-835e-d9eea05be424";
		
		// run the scenario * times.
		for (int i = 0; i < times; i++) {
			if (i == 0 /*&& aid != null*/) {
				//ec.setDownloadInput(true);
				ec.setDownloadInput(false);
			} else {
				// Reuse the archive from previous run.
				aid = ec.getArchiveIDForProcessing();
				ec.setDownloadInput(false);
			}

			// Run scenario.
			log.info(i + " aid:" + aid);
			configureWorkflowScenarioAndRunIt(aid);
		}
		
	}
	// Main
	// ---
	// ---
	public static void main(String[] args) {

		Example ex = new Example();
		ex.exec();

	}
}
