package com.gr15;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/reports/merchants")
public class ReportMerchantResource {
	Report instance = Report.reportinstance;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<UUID, Transaction> getReport(@QueryParam("id") String id, @QueryParam("start") String start, @QueryParam("end") String end) throws ParseException {
		
		instance.removeAll();
		
		Date date1 = new Date();
		instance.addTransaction(new Transaction("01", "10", "100", date1));
		
		Date date2 = new Date();
		instance.addTransaction(new Transaction("02", "11", "100", date2));
		
		Date date3 = new Date();
		instance.addTransaction(new Transaction("01", "11", "100", date3));
		
		Date date4 = new Date();
		instance.addTransaction(new Transaction("02", "10", "100", date4));
		
		Date date5 = new Date();
		instance.addTransaction(new Transaction("03", "10", "100", date5));
		
		Date date6 = new Date();
		instance.addTransaction(new Transaction("10", "01", "100", date6));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dBegin = sdf.parse(start);
	    Date dEnd = sdf.parse(end);
	  
	    
        return instance.getMerchantTransactions(id, dBegin, dEnd);
    }

}
