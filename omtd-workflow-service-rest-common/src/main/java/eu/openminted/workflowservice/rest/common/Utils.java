package eu.openminted.workflowservice.rest.common;

import java.util.ArrayList;
import java.util.List;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.ComponentInfo;
import eu.openminted.registry.domain.IdentificationInfo;
import eu.openminted.registry.domain.MetadataHeaderInfo;
import eu.openminted.registry.domain.MetadataIdentifier;
import eu.openminted.registry.domain.ResourceIdentifier;
import eu.openminted.registry.domain.ResourceIdentifierSchemeNameEnum;

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
		
		ComponentInfo ci = new ComponentInfo();
		IdentificationInfo ii = new IdentificationInfo();
		
		
		List<ResourceIdentifier> applicationIdentifiers = new ArrayList<ResourceIdentifier>();
		ResourceIdentifier ri = new ResourceIdentifier();
		ri.setResourceIdentifierSchemeName(ResourceIdentifierSchemeNameEnum.fromValue(ResourceIdentifierSchemeNameEnum.OTHER.value()));
		ri.setSchemeURI("workflowName");
		
		applicationIdentifiers.add(ri);
		ii.setResourceIdentifiers(applicationIdentifiers);
		ci.setIdentificationInfo(ii);	
		componentMetadata.setComponentInfo(ci);
		
		return componentMetadata;
	}
}
