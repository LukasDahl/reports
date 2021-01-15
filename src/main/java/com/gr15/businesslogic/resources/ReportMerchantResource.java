package com.gr15.businesslogic.resources;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.gr15.businesslogic.models.Report;
import com.gr15.businesslogic.models.Transaction;

@Path("/reports/merchants")
public class ReportMerchantResource {
	Report instance = Report.reportinstance;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Transaction> getReport(@QueryParam("id") String id, @QueryParam("start") String start, @QueryParam("end") String end) throws ParseException {
		
		LocalDateTime date1 = LocalDateTime.now();
		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), "10", "01", "description", date1));
		
		LocalDateTime date2 = LocalDateTime.now();
		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), "10", "01", "description", date2));
		
		LocalDateTime date3 = LocalDateTime.now();
		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), "10", "01", "description", date3));
		
		LocalDateTime date4 = LocalDateTime.now();
		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), "10", "01", "description", date4));
		
		LocalDateTime date5 = LocalDateTime.now();
		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), "10", "01", "description", date5));
		
		LocalDateTime date6 = LocalDateTime.now();
		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), "10", "01", "description", date6));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime dBegin = LocalDateTime.parse(start, formatter);
		LocalDateTime dEnd = LocalDateTime.parse(end, formatter);
	  
	    
        return instance.getMerchantTransactions(id, dBegin, dEnd);
    }

}
