package com.gr15;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/reports")
public class ReportResource1 {
	Report instance = Report.reportinstance;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	public String addTransaction(Transaction transaction) {
		instance.addTransaction(transaction);
		return "Successful";
	}

}
