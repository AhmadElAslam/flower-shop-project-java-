package database;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.Complain;
import entity.ReportComplains;
import entity.ServerResponse;
import entity.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class ComplainsDatabase {

	/**
	 * get list of products from database
	 * @return ArrayList of complains
	 */
	public static void getComplains(Connection conn,  ConnectionToClient client) throws SQLException { 
		/**
		 * get list of products from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from Complains where shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				ArrayList<Complain> complains = new ArrayList<Complain>();
				while ( rs.next() )
				{
					// create complain
					Complain complain = new Complain();
					
					complain.setId(rs.getInt("id"));
					complain.setDesc(rs.getString("desc"));
					complain.setCompensation(rs.getFloat("compensation"));
					complain.setStatus(rs.getInt("status"));
					
					// add to complains array
					complains.add(complain);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetComplain);
				sr.setValue(complains);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * add product to database
	 * @param complain
	 */
	public static void addComplain(Connection conn,  ConnectionToClient client,Complain complain) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddComplain);
		PreparedStatement ps;
		String s1 = "INSERT INTO complains (complains.desc,userID,status,shop_id,complain_date) VALUES (?,?,0,?,now());";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, complain.getDesc());
				ps.setInt(2, complain.getUser().getId());
				ps.setInt(3, ServerController.shop.getId());
				ps.executeUpdate();

				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * delete product from database
	 * @param complain
	 */
	public static void deleteComplain(Connection conn,  ConnectionToClient client,Complain complain) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteComplain);
		PreparedStatement ps;
		String s1 = "delete from Complains where id=? and shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, complain.getId());
				ps.setInt(2, ServerController.shop.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * delete product from database
	 */
	public static void pay(Connection conn,  ConnectionToClient client,Complain complain) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.Recompense);
		PreparedStatement ps;
		String s1 = "update complains set compesation_date=now(),compensation=? where id=? and shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setFloat(1, complain.getCompensation());
				ps.setInt(2, complain.getId());
				ps.setInt(3, ServerController.shop.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			
		}
	}
	/**
	 * get list of not authorized users from database
	 * @return users
	 */
	public static void getUsers(Connection conn,  ConnectionToClient client) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from users where shop_id=?";
		
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);	
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				ArrayList<User> users = new ArrayList<User>();
				while ( rs.next() )
				{
					// create product
					User user = new User(rs.getInt("id"),
							rs.getString("username"),
							rs.getString("password"));
					user.setFname(rs.getString("fname"));
					user.setLname(rs.getString("lname"));
					user.setPhone(rs.getString("phone"));
					users.add(user);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetComplainUsers);
				sr.setValue(users);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	/**
	 * complains report database
	 * get list of cart items from database
	 * @return complains report
	 */
	public static void getComplainsReport(Connection conn,  ConnectionToClient client,Actions action) throws SQLException { 
		
		int shop_id = ServerController.shop.getId(); // set shop
		
		PreparedStatement ps;
		ResultSet rs;
		String s1 = "select * from complains where shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
					ps.setInt(1, shop_id);
				rs = ps.executeQuery();
				ArrayList<Complain> complains = new ArrayList<Complain>();
				
				while ( rs.next() )
				{
					// create 
					Complain complain = new Complain();
					
					complain.setId(rs.getInt("id"));
					complain.setDesc(rs.getString("desc"));
					complain.setCompensation(rs.getFloat("compensation"));
					complain.setStatus(rs.getInt("status"));
					
					Date d = rs.getDate("complain_date");
					complain.setComplainDate(d.toLocalDate());
					
					complains.add(complain);
				}
			/** create report */	
				
				ReportComplains report = new ReportComplains();
				report.setComplains(complains);
				
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.getComplainsReport);
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
