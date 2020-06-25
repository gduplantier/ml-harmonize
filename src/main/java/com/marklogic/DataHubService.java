package com.marklogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.marklogic.hub.flow.FlowInputs;
import com.marklogic.hub.flow.FlowRunner;
import com.marklogic.hub.flow.RunFlowResponse;
import com.marklogic.hub.flow.impl.FlowRunnerImpl;
import com.marklogic.hub.impl.HubConfigImpl;

@Service
public class DataHubService {
	
	@Autowired
	HubConfigImpl hubConfig;
	
	private final String host;
			     
    public DataHubService(@Value("${marklogic.host}") String host) {
		this.host = host;
    }
    
    public RunFlowResponse runFlow(String flowName, List<String> steps, String jobId, String username, String password) {
    	RunFlowResponse response = null;
    	if(flowName != null && steps != null) {
    	    FlowRunner flowRunner = new FlowRunnerImpl(host, username, password);
    	    
    	    hubConfig.setHost(host);
    	    hubConfig.setMlUsername(username);
    	    hubConfig.setMlPassword(password);

    	    FlowInputs inputs = new FlowInputs(flowName);
    	    inputs.setSteps(steps);
    	    inputs.setJobId(jobId);

    	    response = flowRunner.runFlow(inputs);
    	}
    	return response;
    }

}