package com.diagnotech.global;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GlobalService {
	

	public  static Date getDate() {
		LocalDateTime myDateObj = LocalDateTime.now();  
	    System.out.println("Before Formatting: " + myDateObj);  
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
	    
	    String formattedDate = myDateObj.format(myFormatObj);  
	     return new Date(formattedDate);
	    //System.out.println("After Formatting: " + formattedDate); 
	}
	
	
	public  static Date getTime() {
		LocalDateTime myDateObj = LocalDateTime.now();  
	    System.out.println("Before Formatting: " + myDateObj);  
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("hh:mm:ss a");  
	    
	    String formattedDate = myDateObj.format(myFormatObj);  
	     return new Date(formattedDate);
	    //System.out.println("After Formatting: " + formattedDate); 
	}
	 
	 
    
    
}
