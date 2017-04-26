package parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ArtifactLibrary {
	
	//contains variable map<String, DesignDecision> of id --> DesignDecision
	DesignDecisions designDecisions;  //DesignDecision has id, description, (and eventually more)
	
	//contains variable map<String, String> of id --> descriptions 
	Requirements requirements;
	
	//contains map<String,set<String> > of design decision id  --> set of requirement ids
	DesignRequirementLink designReqLink;
	
	private ArtifactContainerFactory factory;
	
	private static ArtifactLibrary instance = null;
	public ArtifactLibrary() {
		// Exists only to defeat instantiation.
		
		factory = new ArtifactContainerFactory();
		
		designDecisions = (DesignDecisions) getArtifacts("designDecisions");
		requirements = (Requirements) getArtifacts("requirements");
		designReqLink = (DesignRequirementLink) getArtifacts("designRequirementLink");
		
		testWithDisplay();
	}

	public static ArtifactLibrary getInstance() {
		if(instance == null) {
			instance = new ArtifactLibrary();
	    }
		return instance;
	}
	
	public XMLParsableArtifactContainer getArtifacts(String type) {
		XMLParsableArtifactContainer container;
		container = factory.getXMLArtifacts(type);
		container.performParse();
		container.organizeContents();
		return container;
	}
	
	public DesignDecisions getDesignDecisions() {
		return designDecisions;
	}
	
	public Requirements getRequirements() {
		return requirements;
	}
	
	public DesignRequirementLink getDesignReqLink(){
		return designReqLink;
	}
	
	public void testWithDisplay() {
		for(String key : designDecisions.map.keySet()) System.out.println(key + ": " + designDecisions.map.get(key).description);
		for(String key : requirements.map.keySet()) System.out.println(key + ": " + requirements.map.get(key));
	}
	
	//Useful get functions
	public HashSet<String> getReqIdList(String designDecisionId) {
		return designReqLink.map.get(designDecisionId);
	}
	
	public void setReqIdList(String designDecisionId, HashSet<String> reqSet) {
		designReqLink.map.put(designDecisionId, reqSet);
	}
	
	public String getDesignDesc(String designDecisionId) {
		return designDecisions.map.get(designDecisionId).description;
	}
}
