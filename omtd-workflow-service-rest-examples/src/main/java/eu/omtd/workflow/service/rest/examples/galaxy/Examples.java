package eu.omtd.workflow.service.rest.examples.galaxy;
 	
public class Examples {

	public static void main(String args[]){
		String galaxyInstanceUrl = "http://snf-754063.vm.okeanos.grnet.gr";
		String galaxyApiKey = "e8ea6ff4be6b372344fa6ad123c9d390";
		
		Galaxy galaxy = new Galaxy(galaxyInstanceUrl, galaxyApiKey);
		//galaxy.runWorkflow("/home/ilsp/Desktop/DG/OMTD/omtd-simple-workflows/testInput/", "DGTest1");
		galaxy.runWorkflow("C:/Users/galanisd/Desktop/smallPDFs/", "DGTest1", "C:/Users/galanisd/Desktop/smallPDFsOut/");
		
	}
}
