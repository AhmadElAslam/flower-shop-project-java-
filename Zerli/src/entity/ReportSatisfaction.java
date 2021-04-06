package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ReportSatisfaction implements Serializable {
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<SurveyResults> surveyResults;
	
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
	public ArrayList<SurveyResults> getSurveyResults() {
		return surveyResults;
	}
	public void setSurveyResults(ArrayList<SurveyResults> surveyResults) {
		this.surveyResults = surveyResults;
	}
	
	public ArrayList<SurveyResults> getSatisfactionResultsByQY(int year,int quarter) {
		// return all sr elements from specific year and quarter
		ArrayList<SurveyResults> SatisfactionResultsByQY = new ArrayList<SurveyResults>();
		for(SurveyResults r: surveyResults)
		{
			if(r.getDate().getYear() == year && r.getQuarter() == quarter)
				SatisfactionResultsByQY.add(r);
		}
			
		return SatisfactionResultsByQY;
	}
	
	
	public ArrayList<Integer> GetYears()
	{
		ArrayList<Integer> years = new ArrayList<Integer>();
		for(SurveyResults r: surveyResults)
			if(!years.contains(r.getDate().getYear()))
				years.add(r.getDate().getYear());
		
		return years;
	}
	
	/******* General satisfaction ********/
	
	public int getGeneralSatisfaction(int year,int quarter)
	{
		// get general satisfaction factor (1-10) from all survey results
		ArrayList<SurveyResults> arr = getSatisfactionResultsByQY(year,quarter);
		int[] questionResultArray=new int[6];
		int sum =0;
		
		for(SurveyResults sr: arr)
		{
			questionResultArray[0]+=sr.getQ1();
			questionResultArray[1]+=sr.getQ2();
			questionResultArray[2]+=sr.getQ3();
			questionResultArray[3]+=sr.getQ4();
			questionResultArray[4]+=sr.getQ5();
			questionResultArray[5]+=sr.getQ6();
		}
		
		for(int i=0; i<6; i++)
		{
			sum += questionResultArray[i]/arr.size();
		}
		
		return sum/6;
	}
	
	// survey result avg by survey
	public Map<String, Integer> getSatisfactionBySurvey(int year,int quarter)
	{
		ArrayList<SurveyResults> arr = getSatisfactionResultsByQY(year,quarter);
		
		// returned data
		Map<String, Integer> mapResults = new HashMap<String, Integer>();
		
		for(SurveyResults s: arr)
		{
			int q1,q2,q3,q4,q5,q6;
			if(mapResults.containsKey(s.getSurvey().getSurveyName()))
			{
				// contains survey name
				int sum = mapResults.get(s.getSurvey().getSurveyName());
				q1=s.getQ1();
				q2=s.getQ2();
				q3=s.getQ3();
				q4=s.getQ4();
				q5=s.getQ5();
				q6=s.getQ6();
				sum += (q1+q2+q3+q4+q5+q6)/6;
				mapResults.put(s.getSurvey().getSurveyName(), sum);
			}
			else
			{
				// add key
				q1=s.getQ1();
				q2=s.getQ2();
				q3=s.getQ3();
				q4=s.getQ4();
				q5=s.getQ5();
				q6=s.getQ6();
				int sum = (q1+q2+q3+q4+q5+q6)/6;
				
				mapResults.put(s.getSurvey().getSurveyName(), sum);
			}
		}
		
		
		return mapResults;
	}
	
}
