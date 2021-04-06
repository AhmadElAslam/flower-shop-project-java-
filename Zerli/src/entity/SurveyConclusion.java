package entity;

import java.io.Serializable;


public class SurveyConclusion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -813659033911675494L;
	private int id;
	private int surveyId;
	private String conclusion;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	
}