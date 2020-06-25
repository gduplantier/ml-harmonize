package com.marklogic;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marklogic.hub.flow.RunFlowResponse;

//curl 'http://localhost:8090/harmonize?host=localhost&username=admin&password=admin&flowName=testFlow&steps=1'

@RestController
public class Harmonize {
	
	@Autowired
	DataHubService dhService;
	
	@Value("${marklogic.database.jobs}")
	private String jobsDatabase;
	
	@RequestMapping("/harmonize")
	public String harmonize(@RequestParam String host,
							@RequestParam String username,
							@RequestParam String password,
							@RequestParam String flowName,
							@RequestParam String steps) {
		String jobId = UUID.randomUUID().toString();
		RunFlowResponse response = dhService.runFlow(flowName, Arrays.asList(steps), jobId, username, password);
		        		
		return "Harmonize flow started with jobId: " + jobId + "\n" + 
			"View status at http://" + host + ":8000/v1/documents?database=" + jobsDatabase + "&uri=/jobs/" + jobId + ".json\n";
	}

}