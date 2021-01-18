package com.gr15.businesslogic.resources;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.gr15.businesslogic.models.QueueService;
import com.gr15.businesslogic.models.Report;
import com.gr15.businesslogic.models.Transaction;

@Path("/reports/managers")
public class ReportManagerResource {
	Report instance = Report.report;
//	static QueueService queue;
//	
//	
//	public ReportManagerResource() {
//		queue = new QueueService();
//	}
	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getReport(@QueryParam("id") String id, @QueryParam("start") String start, @QueryParam("end") String end) throws ParseException {
		
		//instance.removeAll();
		
//		LocalDateTime date1 = LocalDateTime.now();
//		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
//				BigDecimal.valueOf(100), "10", "01", "description", date1));
//		
//		LocalDateTime date2 = LocalDateTime.now();
//		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
//				BigDecimal.valueOf(100), "10", "01", "description", date2));
//		
//		LocalDateTime date3 = LocalDateTime.now();
//		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
//				BigDecimal.valueOf(100), "10", "01", "description", date3));
//		
//		LocalDateTime date4 = LocalDateTime.now();
//		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
//				BigDecimal.valueOf(100), "10", "01", "description", date4));
//		
//		LocalDateTime date5 = LocalDateTime.now();
//		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
//				BigDecimal.valueOf(100), "10", "01", "description", date5));
//		
//		LocalDateTime date6 = LocalDateTime.now();
//		instance.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
//				BigDecimal.valueOf(100), "10", "01", "description", date6));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate starttemp = LocalDate.parse(start, formatter);
		LocalDate endtemp = LocalDate.parse(end, formatter);
		LocalDateTime dBegin = starttemp.atStartOfDay();
		LocalDateTime dEnd = endtemp.atStartOfDay();
	  
	    
        return instance.getAllTransactions(id, dBegin, dEnd);
    }

}
