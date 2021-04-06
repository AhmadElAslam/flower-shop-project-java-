package entity;

import java.io.Serializable;
import java.time.LocalDate;


public class SurveyResults implements Serializable {
	private static final long serialVersionUID = -4188645280897431368L;
	/**
	 * 
	 */
	
	private int id;
	private int q1;
	private int q2;
	private int q3;
	private int q4;
	private int q5;
	private int q6;
	private int surveyId;
	private Survey survey;
	private LocalDate date;
	
	public SurveyResults(int id,int q1,int q2,int q3,int q4,int q5,int q6,int surveyId) {
		this.setId(id);
		this.setQ1(q1);
		this.setQ2(q2);
		this.setQ3(q3);
		this.setQ4(q4);
		this.setQ5(q5);
		this.setQ6(q6);
		this.setSurveyId(surveyId);
	}
	
	public SurveyResults(int q1,int q2,int q3,int q4,int q5,int q6,int surveyId) {
		this.setQ1(q1);
		this.setQ2(q2);
		this.setQ3(q3);
		this.setQ4(q4);
		this.setQ5(q5);
		this.setQ6(q6);
		this.setSurveyId(surveyId);
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public int getQ6() {
		return q6;
	}

	public void setQ6(int q6) {
		if(q6>10)
			q6=10;
		if(q6<1)
			q6=1;
		this.q6 = q6;
	}

	public int getQ5() {
		return q5;
	}

	public void setQ5(int q5) {
		if(q5>10)
			q5=10;
		if(q5<1)
			q5=1;
		this.q5 = q5;
	}

	public int getQ4() {
		return q4;
	}

	public void setQ4(int q4) {
		if(q4>10)
			q4=10;
		if(q4<1)
			q4=1;
		this.q4 = q4;
	}

	public int getQ3() {
		return q3;
	}

	public void setQ3(int q3) {
		if(q3>10)
			q3=10;
		if(q3<1)
			q3=1;
		this.q3 = q3;
	}

	public int getQ2() {
		return q2;
	}

	public void setQ2(int q2) {
		if(q2>10)
			q2=10;
		if(q2<1)
			q2=1;
		this.q2 = q2;
	}

	public int getQ1() {
		return q1;
	}

	public void setQ1(int q1) {
		if(q1>10)
			q1=10;
		if(q1<1)
			q1=1;
		this.q1 = q1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public int getQuarter()
	{
		// get quarter
		return (date.getMonthValue() / 3) + 1;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}


}
