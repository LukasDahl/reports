package com.gr15.businesslogic.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.gr15.businesslogic.models.Report;
import com.gr15.businesslogic.models.Transaction;

@Path("/reports/customers")
public class ReportCustomerResource {
	Report instance = Report.report;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getReport(@QueryParam("id") String id, @QueryParam("start") String start, @QueryParam("end") String end) {
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate starttemp = LocalDate.parse(start, formatter);
		LocalDate endtemp = LocalDate.parse(end, formatter);
		LocalDateTime dBegin = starttemp.atStartOfDay();
		LocalDateTime dEnd = endtemp.atStartOfDay();
		
	  
        return instance.getCustomerTransactions(id, dBegin, dEnd);
    }

}
