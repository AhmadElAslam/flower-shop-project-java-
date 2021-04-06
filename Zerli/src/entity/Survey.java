package entity;

import java.io.Serializable;


public class Survey implements Serializable {
	private static final long serialVersionUID = -4188645280897431368L;
	/**
	 * 
	 */
	
	private int id;
	private String q1;
	private String q2;
	private String q3;
	private String q4;
	private String q5;
	private String q6;
	private String surveyName;
	
	public Survey(String q1,String q2,String q3,String q4,String q5,String q6,String surveyName) {
		this.setQ1(q1);
		this.setQ2(q2);
		this.setQ3(q3);
		this.setQ4(q4);
		this.setQ5(q5);
		this.setQ6(q6);
		this.setSurveyName(surveyName);
	}
	
	public Survey(int id,String q1,String q2,String q3,String q4,String q5,String q6,String surveyName) {
		this.setId(id);
		this.setQ1(q1);
		this.setQ2(q2);
		this.setQ3(q3);
		this.setQ4(q4);
		this.setQ5(q5);
		this.setQ6(q6);
		this.setSurveyName(surveyName);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getQ1() {
		return q1;
	}


	public void setQ1(String q1) {
		this.q1 = q1;
	}


	public String getQ2() {
		return q2;
	}


	public void setQ2(String q2) {
		this.q2 = q2;
	}


	public String getQ3() {
		return q3;
	}


	public void setQ3(String q3) {
		this.q3 = q3;
	}


	public String getQ4() {
		return q4;
	}


	public void setQ4(String q4) {
		this.q4 = q4;
	}


	public String getQ5() {
		return q5;
	}


	public void setQ5(String q5) {
		this.q5 = q5;
	}


	public String getQ6() {
		return q6;
	}


	public void setQ6(String q6) {
		this.q6 = q6;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	
	public String toString() { 
		return surveyName;
	}
}
