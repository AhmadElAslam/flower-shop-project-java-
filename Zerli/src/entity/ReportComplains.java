package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ReportComplains implements Serializable {
	private ArrayList<Complain> complains;

	public ArrayList<Complain> getComplains() {
		return complains;
	}

	public void setComplains(ArrayList<Complain> complains) {
		this.complains = complains;
	}
	
	public ArrayList<Integer> GetYears()
	{
		ArrayList<Integer> years = new ArrayList<Integer>();
		for(Complain r: complains)
			if(!years.contains(r.getComplainDate().getYear()))
				years.add(r.getComplainDate().getYear());
		
		return years;
	}
	
	public ArrayList<Complain> getComplainsByQY(int year,int quarter) {
		// return all complains elements from specific year and quarter
		ArrayList<Complain> complainByQY = new ArrayList<Complain>();
		for(Complain r: complains)
		{
			if(r.getComplainDate().getYear() == year && r.getQuarter() == quarter)
				complainByQY.add(r);
		}
			
		return complainByQY;
	}
	
	public int getNumberOfComplainsByMonth(int month)
	{
		/* return number of complains in specific month(1-12) */ 
		int numberOfMonths=0;
		for(Complain r: complains)
			if(r.getComplainDate().getMonthValue()==month)
				numberOfMonths++;
		return numberOfMonths;
	}



}
