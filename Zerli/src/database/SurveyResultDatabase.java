package database;

import ocsf.server.ConnectionToClient;
import server.ServerController;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.ReportSatisfaction;
import entity.ServerResponse;
import entity.Survey;
import entity.SurveyResults;
import enums.Actions;
public class SurveyResultDatabase {
	
	/**
	 * get list of surveys from database
	 * @return surveys
	 */
	public static void getSurveys(Connection conn,  ConnectionToClient client) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from Surveys where shop_id=?"; 
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				ArrayList<Survey> surveys = new ArrayList<Survey>();
				while ( rs.next() )
				{
					// create survey
					Survey survey = new Survey(
							rs.getInt("id"),
							rs.getString("q1"),
							rs.getString("q2"),
							rs.getString("q3"),
							rs.getString("q4"),
							rs.getString("q5"),
							rs.getString("q6"),
							rs.getString("survey_name"));
					surveys.add(survey);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetSurveyNames);
				sr.setValue(surveys);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * get list from database survey results
	 * @return survey results
	 */
	public static void getSurveysResults(Connection conn,  ConnectionToClient client) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from Survey_results where shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				ArrayList<SurveyResults> surveys = new ArrayList<SurveyResults>();
				while ( rs.next() )
				{
					// create survey
					SurveyResults surveyRes = new SurveyResults(
							rs.getInt("id"),
							rs.getInt("q1"),
							rs.getInt("q2"),
							rs.getInt("q3"),
							rs.getInt("q4"),
							rs.getInt("q5"),
							rs.getInt("q6"),
							rs.getInt("survey_id"));
					
					Date d = rs.getDate("date");
					surveyRes.setDate(d.toLocalDate());
					surveys.add(surveyRes);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetSurveyResults);
				sr.setValue(surveys);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * get survey by id
	 * @param survey id
	 * @return survey data
	 */
	public static void getSurveyData(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from Surveys where id=? and shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, survey.getId());
				ps.setInt(2, ServerController.shop.getId());
				rs = ps.executeQuery();
				Survey surveyx;
				if ( rs.next() )
				{
					// create survey
					surveyx = new Survey(
							rs.getInt("id"),
							rs.getString("q1"),
							rs.getString("q2"),
							rs.getString("q3"),
							rs.getString("q4"),
							rs.getString("q5"),
							rs.getString("q6"),
							rs.getString("survey_name"));
					
					ServerResponse sr = new ServerResponse(); // create server response
					sr.setAction(Actions.GetSurveyData);
					sr.setValue(surveyx);
					client.sendToClient(sr); // send messeage to client
				}
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * add survey result to database
	 */
	public static void addSurvey(Connection conn,  ConnectionToClient client,SurveyResults surveyRes) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddSurveyResults);
		PreparedStatement ps;
		String s1 = "INSERT INTO survey_results (q1,q2,q3,q4,q5,q6,survey_id,shop_id,date) VALUES (?,?,?,?,?,?,?,?,now());";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, surveyRes.getQ1());
				ps.setInt(2, surveyRes.getQ2());
				ps.setInt(3, surveyRes.getQ3());
				ps.setInt(4, surveyRes.getQ4());
				ps.setInt(5, surveyRes.getQ5());
				ps.setInt(6, surveyRes.getQ6());
				ps.setInt(7, surveyRes.getSurveyId());
				ps.setInt(8, ServerController.shop.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
	/**
	public static void updateSurvey(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {
		//update
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.UpdateSurvey);
		PreparedStatement ps;
		String s1 = "update surveys set q1=?,q2=?,q3=?,q4=?,q5=?,q6=?,survey_name=? where id=?;";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, survey.getQ1());
				ps.setString(2, survey.getQ2());
				ps.setString(3, survey.getQ3());
				ps.setString(4, survey.getQ4());
				ps.setString(5, survey.getQ5());
				ps.setString(6, survey.getQ6());
				ps.setString(7, survey.getSurveyName());
				ps.setInt(8, survey.getId());
				ps.executeUpdate();

				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			
		}
	}
	*/
	/**
	 * delete Survey 
	 * @return action - deleted or not
	 */
	public static void deleteSurvey(Connection conn,  ConnectionToClient client,SurveyResults survey) throws SQLException {
		// delete survey result from database
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteSurveyResults);
		PreparedStatement ps;
		String s1 = "delete from survey_results where id=? and shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, survey.getId());
				ps.setInt(2, ServerController.shop.getId());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.SurveyDeleted);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			sr.setAnswer(Actions.SurveyNotDeleted);
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
	
	/***********************************************
	 * 				Satisfaction report
	 ***********************************************/
	/**
	 * get list of products from database
	 */
	public static void getSatisfaction(Connection conn,  ConnectionToClient client,LocalDate start,LocalDate end,Actions action) throws SQLException { 
		int shop_id = ServerController.shop.getId(); // set shop
		
		PreparedStatement ps;
		ResultSet rs;
		PreparedStatement ps2;
		ResultSet rs2;
		String s1 = "select * from Survey_results where shop_id=?";
		String s2 = "select * from Surveys where shop_id=? and id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, shop_id);
				rs = ps.executeQuery();
				ArrayList<SurveyResults> surveys = new ArrayList<SurveyResults>();
				while ( rs.next() )
				{
					// create survey
					SurveyResults surveyRes = new SurveyResults(
							rs.getInt("id"),
							rs.getInt("q1"),
							rs.getInt("q2"),
							rs.getInt("q3"),
							rs.getInt("q4"),
							rs.getInt("q5"),
							rs.getInt("q6"),
							rs.getInt("survey_id"));
					
					Date d = rs.getDate("date");
					surveyRes.setDate(d.toLocalDate());
					
					/***** get survey *****/
					 
					try {
							ps2 = (PreparedStatement) conn.prepareStatement(s2);
							ps2.setInt(1, ServerController.shop.getId());
							ps2.setInt(2, surveyRes.getSurveyId());
							rs2 = ps2.executeQuery();
							if ( rs2.next() )
							{
								System.out.println(surveyRes.getSurveyId());
								// create survey
								Survey survey = new Survey(
										rs2.getInt("id"),
										rs2.getString("q1"),
										rs2.getString("q2"),
										rs2.getString("q3"),
										rs2.getString("q4"),
										rs2.getString("q5"),
										rs2.getString("q6"),
										rs2.getString("survey_name"));
										surveyRes.setSurvey(survey);
							}
							
					}
					catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
					}
					
					surveys.add(surveyRes);
				}
				
				ReportSatisfaction report = new ReportSatisfaction();
				report.setSurveyResults(surveys);
				report.setStartDate(start);
				report.setEndDate(end);
				
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetSatisfactionReport);
				//for 2 screen report
				if(action!=null)
					sr.setAction(action);
				sr.setValue(report);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
