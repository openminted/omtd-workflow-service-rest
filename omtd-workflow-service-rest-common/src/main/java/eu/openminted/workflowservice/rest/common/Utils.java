package eu.openminted.workflowservice.rest.common;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.MetadataHeaderInfo;
import eu.openminted.registry.domain.MetadataIdentifier;

/**
 * @author ilsp
 *
 */
public class Utils {

	public static Component createComponentMetadata(String wid){
		MetadataIdentifier metadataId = new MetadataIdentifier();
		metadataId.setValue(wid);
		
		MetadataHeaderInfo metadataHeaderInfo = new MetadataHeaderInfo();		
		metadataHeaderInfo.setMetadataRecordIdentifier(metadataId);
		
		Component componentMetadata = new Component();
		componentMetadata.setMetadataHeaderInfo(metadataHeaderInfo);
		
		return componentMetadata;
	}
}
