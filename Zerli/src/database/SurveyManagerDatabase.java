package database;

import ocsf.server.ConnectionToClient;
import server.ServerController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import entity.ServerResponse;
import entity.Survey;
import enums.Actions;
public class SurveyManagerDatabase { 
	
	//static int shop_id = ServerController.shop.getId();
	
	/**
	 * get list of surveys from database
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
				sr.setAction(Actions.GetSurveys);
				sr.setValue(surveys);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * add survey to database
	 * @param add survey to database
	 */
	public static void addSurvey(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {
		/**
		 * add survey to database
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddSurvey);
		PreparedStatement ps;
		String s1 = "INSERT INTO surveys (q1,q2,q3,q4,q5,q6,survey_name,shop_id) VALUES (?,?,?,?,?,?,?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, survey.getQ1());
				ps.setString(2, survey.getQ2());
				ps.setString(3, survey.getQ3());
				ps.setString(4, survey.getQ4());
				ps.setString(5, survey.getQ5());
				ps.setString(6, survey.getQ6());
				ps.setString(7, survey.getSurveyName());
				ps.setInt(8, ServerController.shop.getId());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.SurveyAdded);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			sr.setAnswer(Actions.SurveyNotAdded);
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
	/**
	 * update survey data in database
	 * @param update survey data in database
	 */
	public static void updateSurvey(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {
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
	/**
	 * delete survey from database
	 * @param survey
	 * @return action - deleted or not
	 */
	public static void deleteSurvey(Connection conn,  ConnectionToClient client,Survey survey) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteSurvey);
		PreparedStatement ps;
		String s1 = "delete from surveys where id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, survey.getId());
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
}
