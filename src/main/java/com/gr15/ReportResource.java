package com.gr15;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/reports/{ID}")
public class ReportResource {
	Report instance = Report.reportinstance;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Integer, Transaction> getReport(@PathParam("ID") String ID) {
		
        return instance.getTransactions(ID);
    }

}
