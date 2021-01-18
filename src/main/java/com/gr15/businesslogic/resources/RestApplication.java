/**
 * @author Wassim
 */

package com.gr15.businesslogic.resources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.gr15.businesslogic.models.QueueService;

@ApplicationPath("/")
public class RestApplication extends Application {
	
	static QueueService queue;
	
	public RestApplication() {
		queue = new QueueService();
	}

}
