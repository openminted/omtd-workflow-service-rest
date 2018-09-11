package eu.omtd.workflow.service.rest.examples;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.MetadataHeaderInfo;
import eu.openminted.registry.domain.MetadataIdentifier;

/**
 * @author ilsp
 *
 */
public class Utils {

	public static Component createComponent(String wid){
		MetadataIdentifier metadataId = new MetadataIdentifier();
		metadataId.setValue(wid);
		
		MetadataHeaderInfo metadataHeaderInfo = new MetadataHeaderInfo();		
		metadataHeaderInfo.setMetadataRecordIdentifier(metadataId);
		
		Component workflow = new Component();
		workflow.setMetadataHeaderInfo(metadataHeaderInfo);
		
		return workflow;
	}
}
