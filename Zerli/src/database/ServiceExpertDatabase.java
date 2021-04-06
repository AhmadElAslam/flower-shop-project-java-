package database;

import ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import entity.ServerResponse;
import entity.Survey;
import entity.SurveyConclusion;
import entity.SurveyResults;
import enums.Actions;
public class ServiceExpertDatabase { 
	/**
	 * get list of surveys from database
	 * @return ArrayList of surveys
	 */
	public static void getSurveys(Connection conn,  ConnectionToClient client) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from Surveys";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
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
				sr.setAction(Actions.GetSurveyNamesExpert);
				sr.setValue(surveys);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * get number of voters to specific survey
	 * @param survey
	 * @return integer number of voters
	 */
	public static void getSurveyNumOfVoters(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {

		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select count(id) as count from survey_results where survey_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, survey.getId());
				rs = ps.executeQuery();
				
				if ( rs.next() )
				{
					int count = rs.getInt("count");
					
					ServerResponse sr = new ServerResponse(); // create server response
					sr.setAction(Actions.GetNumberOfVoters);
					sr.setValue(count);
					client.sendToClient(sr); // send messeage to client
				}
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * get conclusion to specific survey
	 * @param survey
	 * @return String survey conclusion
	 */
	public static void getConclusion(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select conclusion from expert_conclusion where survey_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, survey.getId());
				rs = ps.executeQuery();
				
				if ( rs.next() )
				{
					String conc = rs.getString("conclusion");
					
					ServerResponse sr = new ServerResponse(); // create server response
					sr.setAction(Actions.GetConclusion);
					sr.setValue(conc);
					client.sendToClient(sr); // send messeage to client
				}
				else
				{
					String conc = "";
					
					ServerResponse sr = new ServerResponse(); // create server response
					sr.setAction(Actions.GetConclusion);
					sr.setValue(conc);
					client.sendToClient(sr); // send messeage to client
				}
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * get survey result average for specific survey
	 * @param survey
	 * @return SurveyResults
	 */
	public static void getSurveyResultAvg(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {

		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select avg(q1) as q1,avg(q2) as q2,avg(q3) as q3,avg(q4) as q4,avg(q5) as q5,avg(q6) as q6 from survey_results where survey_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, survey.getId());
				rs = ps.executeQuery();
				
				if ( rs.next() )
				{
					SurveyResults sres = new SurveyResults(
							rs.getInt("q1"),
							rs.getInt("q2"),
							rs.getInt("q3"),
							rs.getInt("q4"),
							rs.getInt("q5"),
							rs.getInt("q6"),
							survey.getId()
							
							);;
					
					ServerResponse sr = new ServerResponse(); // create server response
					sr.setAction(Actions.GetAvgRes);
					sr.setValue(sres);
					client.sendToClient(sr); // send messeage to client
				}
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * add conclusion to survey
	 * @param SurveyConclusion
	 */
	public static void addConclusion(Connection conn,  ConnectionToClient client,SurveyConclusion sc) throws SQLException {
		/**
		 *  
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddSurveyResults);
		PreparedStatement ps;
		String s1 = "INSERT INTO expert_conclusion (survey_id,conclusion) VALUES (?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, sc.getSurveyId());
				ps.setString(2, sc.getConclusion());
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
	 * update conclusion to survey
	 * @param SurveyConclusion
	 */	
public static void updateConclusion(Connection conn,  ConnectionToClient client,SurveyConclusion sc) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.updateConclusion);
		PreparedStatement ps;
		String s1 = "update expert_conclusion set conclusion=? where survey_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, sc.getConclusion());
				ps.setInt(2, sc.getSurveyId());
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

}