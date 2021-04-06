package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ReportRevenue implements Serializable {
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<Revenue> money;
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public ArrayList<Revenue> getMoney() {
		return money;
	}
	public void setMoney(ArrayList<Revenue> money) {
		this.money = money;
	}
	
	public ArrayList<Revenue> getMoneyByQY(int year,int quarter) {
		// return all revenue elements from specific year and quarter
		ArrayList<Revenue> moneyByQY = new ArrayList<Revenue>();
		System.out.println("getQY");
		for(Revenue r: money)
		{
			if(r.getDate().getYear() == year && r.getQuarter() == quarter)
				moneyByQY.add(r);
		}
			
		return moneyByQY;
	}
	
	public float calculateSumEarnings(int year,int quarter)
	{
		// calculate sum of all money in report
		return this.calculateSumOrders(year,quarter)+this.calculateSumCompensate(year,quarter)+this.calculateSumrefunds(year,quarter);
	}
	
	public float calculateSumOrders(int year,int quarter)
	{
		ArrayList<Revenue> arr = new ArrayList<Revenue>();
		// for different year and quarter
		if(year!=0 && quarter!=0)
			arr = this.getMoneyByQY(year, quarter);
		else arr = money;	
		float sum=0;
		for(Revenue r: arr)
		{
			if(r.getType().equals("order"))
				sum+=r.getMoney();
		}
		System.out.println(sum);
		return sum;
		
	}
	
	public float calculateSumrefunds(int year,int quarter)
	{
		float sum=0;
		ArrayList<Revenue> arr = new ArrayList<Revenue>();
		// for different year and quarter
		if(year!=0 && quarter!=0)
			arr = this.getMoneyByQY(year, quarter);
		else arr = money;	
		
		for(Revenue r: arr)
		{
			if(r.getType().equals("refund"))
				sum+=r.getMoney();
		}
		sum= sum*(-1); // negative
		System.out.println(sum);
		return sum;
	}
	
	public float calculateSumCompensate(int year,int quarter)
	{
		float sum=0;
		
		ArrayList<Revenue> arr = new ArrayList<Revenue>();
		
		
		// for different year and quarter
		if(year!=0 && quarter!=0)
			arr = this.getMoneyByQY(year, quarter);
		else arr = money;	
		
		// calculate
		for(Revenue r: arr)
		{
			if(r.getType().equals("compensate"))
				sum+=r.getMoney();
		}
		sum= sum*(-1); // negative
		System.out.println(sum);
		return sum;
		
	}
	
	// use only for this class
	public LocalDate getMinRevenueDate()
	{
		LocalDate min = LocalDate.now();
		for(Revenue r: money)
			if(r.getDate().isBefore(min))
				min = r.getDate();
		
		return min;
	}
	
	// use only for this class
	public LocalDate getMaxRevenueDate()
	{
		LocalDate max = this.getMinRevenueDate();
		for(Revenue r: money)
			if(r.getDate().isAfter(max))
				max = r.getDate();
		
		return max;
	}
	
	
	public ArrayList<Integer> GetYears()
	{
		ArrayList<Integer> years = new ArrayList<Integer>();
		for(Revenue r: money)
			if(!years.contains(r.getDate().getYear()))
				years.add(r.getDate().getYear());
		
		return years;
	}
	
	
	
}
