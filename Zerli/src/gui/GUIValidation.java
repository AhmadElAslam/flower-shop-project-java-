package gui;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

import entity.ValidationResult;

/**
 * validate content of user input
 */
public class GUIValidation {
	
	/************************************************************
	 *  					General 
	 ******************************************************/
	
	// is the string contains only characters a-z A-Z
	
	public static boolean isLengthLongerThan(String str,int len) {
	    if(str.length() > len)
	    	return true;
	    return false;
	}
	
	public static boolean isString(String str) {
	    return str.matches("[a-zA-Z]+");
	}
	
	// is string Contains only digits
	public static boolean isOnlyDigits(String str) {
	    if(str == null || str.trim().isEmpty()) {
	        return false;
	    }
	    for (int i = 0; i < str.length(); i++) {
	        if(!Character.isDigit(str.charAt(i))) {
	            return false;
	        } 
	    }
	    return true;
	}
	
	// return true if string is FLOAT
	public static boolean isFloat(String numberFloat)
	{
		try
		{
		  Float.parseFloat(numberFloat);
		}
		catch(NumberFormatException e)
		{
		  //not a float
			return false;
		}
		return true;
	}
	
	/************************************************************
	 *  		update my data - Credit Card 
	 ******************************************************/
	
	public static ValidationResult isValidMonth(String month)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		// not only digits
		if(isOnlyDigits(month) == false)
		{
			bool = false;
			msg.add("Month must contain only numbers");
		}
		
		// check for valid month value
		try {
			int monthInteger = Integer.parseInt(month);
			if(monthInteger>12 || monthInteger<1)
			{
				bool = false;
				msg.add("Month value must be between 1-12");
			}
		}
		catch(NumberFormatException e) {
			// not intd
			bool = false;
		}
		
		// passed all checkings
		return new ValidationResult(bool,msg);
	}
	
	public static ValidationResult isValidYear(String year)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		// not only digits
		if(isOnlyDigits(year) == false)
		{
			bool = false;
			msg.add("Year can contain only digits");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	public static ValidationResult isDateExpired(int year,int month)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH);
		
		if(year < curYear || (year==curYear && month>curMonth))
		{
			bool = false;
			msg.add("The date already passed");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	public static ValidationResult CreditCard(String card)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		// not only digits
		if(isLengthLongerThan(card, 19))
		{
			bool = false;
			msg.add("Credit Card can't be longer than 19 digits");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	public static ValidationResult CVV(String cvv)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		if(isOnlyDigits(cvv) == false)
		{
			bool = false;
			msg.add("CVV must contain only digits");
		}
		
		// not only digits
		if(cvv.length()!=3)
		{
			bool = false;
			msg.add("CVV must contain 3 digits");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	
	/************************************************************
	 *  			Order validation 
	 ******************************************************/
	
	public static ValidationResult OrderHour(String hours)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		if(isOnlyDigits(hours) == false)
		{
			bool = false;
			msg.add("Hour must contain only digits");
		}
		
		// not only digits
		if(hours.length()>2)
		{
			bool = false;
			msg.add("Hour can't contain more than 2 digits digits");
		}
		
		// is integer
		try {
			int h = Integer.parseInt(hours);
			if(h<0 || h>24)
			{
				bool = false;
				msg.add("hours must be between 0-24");
			}
		}
		catch(NumberFormatException e){
			bool = false;
			msg.add("hours should be integer");
	    }
		
		
		
		return new ValidationResult(bool,msg);
	}
	
	public static ValidationResult OrderMin(String minutes)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		if(isOnlyDigits(minutes) == false)
		{
			bool = false;
			msg.add("minutes must contain only digits");
		}
		
		// not only digits
		if(minutes.length()>2)
		{
			bool = false;
			msg.add("minutes can't contain more than 2 digits digits");
		}
		
		// is integer
		try {
			int m = Integer.parseInt(minutes);
			if(m<0 || m>60)
			{
				bool = false;
				msg.add("minutes must be between 0-60");
			}
		}
		catch(NumberFormatException e){
			bool = false;
			msg.add("minutes should be integer");
	    }
		
		
		return new ValidationResult(bool,msg);
	}
	
	public static ValidationResult orderDate(LocalDate date)
	{
		boolean bool = true;
		ArrayList<String> msg = new ArrayList<String>();
		
		LocalDate today = LocalDate.now();


		long daysNegative = ChronoUnit.DAYS.between(date, today);
		System.out.println(daysNegative);
		if(daysNegative>0)
		{
			bool = false;
			msg.add("Selected date already passed");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	
	
	
	


}
