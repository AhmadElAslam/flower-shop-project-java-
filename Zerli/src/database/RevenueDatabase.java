package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.ReportRevenue;
import entity.Revenue;
import entity.ServerResponse;
import enums.Actions;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class RevenueDatabase {
	/**
	 * get list of products from database
	 * @param action - which action client should perform
	 * @return revenue report
	 */
	public static void getRevenue(Connection conn,  ConnectionToClient client,LocalDate start,LocalDate end,Actions action) throws SQLException { 

		PreparedStatement ps;
		ResultSet rs;
		PreparedStatement ps2;
		ResultSet rs2;
		PreparedStatement ps3;
		ResultSet rs3;
		String s1 = "select * from Complains where compensation!=0 and shop_id=?";
		String s2 = "select * from refunds where shop_id=?";
		String s3 = "select * from orders where shop_id=?";
		try {
			/** complains money */
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				ArrayList<Revenue> money = new ArrayList<Revenue>();
				
				while ( rs.next() )
				{
					// create revenue
					Revenue reven = new Revenue();
					Date d = rs.getDate("compesation_date");
					reven.setDate(d.toLocalDate());
					reven.setType("compensate");
					reven.setMoney(rs.getFloat("compensation"));
					
					// add to product array
					money.add(reven);
				}
			/** refunds money */	
				ps2 = (PreparedStatement) conn.prepareStatement(s2);
				ps2.setInt(1, ServerController.shop.getId());
				rs2 = ps2.executeQuery();
				
				while ( rs2.next() )
				{
					// create 
					Revenue reven = new Revenue();
					Date d = rs2.getDate("refund_date");
					reven.setDate(d.toLocalDate());
					reven.setType("refund");
					reven.setMoney(rs2.getFloat("refund"));
					
					// add to array
					money.add(reven);
				}
				
			/** orders money */
				ps3 = (PreparedStatement) conn.prepareStatement(s3);
				ps3.setInt(1, ServerController.shop.getId());
				rs3 = ps3.executeQuery();
				
				while ( rs3.next() )
				{
					// create revenue
					Revenue reven = new Revenue();
					Date d = rs3.getDate("order_date");
					reven.setDate(d.toLocalDate());
					reven.setType("order");
					reven.setMoney(rs3.getFloat("price"));
					
					// add to array
					money.add(reven);
				}
				
				ReportRevenue report = new ReportRevenue();
				report.setMoney(money);
				report.setStartDate(start);
				report.setEndDate(end);
				
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetRevenue);
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
