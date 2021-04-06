package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.ServerResponse;
import enums.Actions;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class CustomOrderDatabase {
	/**
	 * get data for custom order:
	 * list of product types from product table
	 * list of color types from product table
	 * maximum product price
	 * minimum product price
	 * @return Arraylist of products data
	 */
	public static void getCustomOrderData(Connection conn,  ConnectionToClient client) throws SQLException {
		PreparedStatement ps;
		ResultSet rs;
		PreparedStatement ps2;
		ResultSet rs2;
		
		//returned data
		ArrayList<String> types = new ArrayList<String>();
		ArrayList<String> colors = new ArrayList<String>();
		float maxPrice=0;
		float minPrice=0;
		
		// sql
		String s1 = "select distinct(ptype) as pt from products where shop_id=?";
		String s2 = "select distinct(color) as color from products where shop_id=?";
		String s3 = "select price from products where shop_id=? order by price desc limit 1"; // min and max sql func dont work
		String s4 = "select price from products where shop_id=? order by price asc limit 1";
		try {
				//types
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				
				while ( rs.next() )
				{
					types.add(rs.getString("pt"));
				}
				//colors
				ps2 = (PreparedStatement) conn.prepareStatement(s2);
				ps2.setInt(1, ServerController.shop.getId());
				rs2 = ps2.executeQuery();
				
				while ( rs2.next() )
				{
					colors.add(rs2.getString("color"));
				}
				// max
				ps = (PreparedStatement) conn.prepareStatement(s3);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				if(rs.next())
				{
					maxPrice = rs.getFloat("price");
				}
				// min
				ps = (PreparedStatement) conn.prepareStatement(s4);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				if(rs.next())
				{
					minPrice = rs.getFloat("price");
				}
				ArrayList<Object> data = new ArrayList<Object>();
				data.add(types);
				data.add(colors);
				data.add(maxPrice);
				data.add(minPrice);
				
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.CustomOrderData);
				sr.setValue(data);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
