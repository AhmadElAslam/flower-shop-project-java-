package database;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.Address;
import entity.CreditCard;
import entity.ServerResponse;
import entity.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class UpdateMyUser {
	
	/**
	 * add user credit card
	 */
public static void addCreditCard(Connection conn,  ConnectionToClient client,CreditCard cc,User u) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddCreditCard);
		PreparedStatement ps;
		String s1 = "INSERT INTO credit_card (card_number,expMonth,expYear,cvv,user_id) VALUES (?,?,?,?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, cc.getCardNumber());
				ps.setInt(2,cc.getExpMonth());
				ps.setInt(3,cc.getExpYear());
				ps.setString(4, cc.getCvv());
				ps.setInt(5,u.getId());
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
 * add user address
 */
public static void addAddress(Connection conn,  ConnectionToClient client,Address ad,User u) throws SQLException {
	ServerResponse sr = new ServerResponse(); // create server response
	sr.setAction(Actions.AddAddress);
	PreparedStatement ps;
	String s1 = "INSERT INTO Address (city,street,number,user_id) VALUES (?,?,?,?);";
	try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setString(1, ad.getCity());
			ps.setString(2, ad.getStreet());
			ps.setInt(3,ad.getNumber());
			ps.setInt(4,u.getId());
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
 * update user credit card
 */
public static void updateCreditCard(Connection conn,  ConnectionToClient client,CreditCard cc,User u) throws SQLException {
	ServerResponse sr = new ServerResponse(); // create server response
	sr.setAction(Actions.UpdateCreditCard);
	PreparedStatement ps;
	String s1 = "update credit_card set card_number=?,expMonth=?,expYear=?,cvv=? where user_id=?;";
	try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setString(1, cc.getCardNumber());
			ps.setInt(2,cc.getExpMonth());
			ps.setInt(3,cc.getExpYear());
			ps.setString(4, cc.getCvv());
			ps.setInt(5,u.getId());
			ps.executeUpdate();
			System.out.println(cc.getCardNumber()+" "+cc.getCvv());

			client.sendToClient(sr); // send messeage to client
		}
	catch (Exception e)
	{
		
	}
}
/**
 * update user address
 */
public static void updateAddress(Connection conn,  ConnectionToClient client,Address ad,User u) throws SQLException {
	ServerResponse sr = new ServerResponse(); // create server response
	sr.setAction(Actions.UpdateAddress);
	PreparedStatement ps;
	String s1 = "update address set city=?,street=?,number=? where user_id=?;";
	try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setString(1, ad.getCity());
			ps.setString(2, ad.getStreet());
			ps.setInt(3,ad.getNumber());
			ps.setInt(4,u.getId());
			ps.executeUpdate();

			client.sendToClient(sr); // send messeage to client
		}
	catch (Exception e)
	{
		
	}
}
}
