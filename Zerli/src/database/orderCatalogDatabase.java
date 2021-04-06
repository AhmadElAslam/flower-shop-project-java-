package database;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.Order;
import entity.ServerResponse;
import entity.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class orderCatalogDatabase {
	/**
	 * create order with products in cart
	 * change cart items to product related 
	 */	
public static void order(Connection conn,  ConnectionToClient client,Order order) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.buyProductFromCatalog);
		PreparedStatement ps;
		PreparedStatement ps2;
		ResultSet rs2; 
		String s1 = "INSERT INTO orders (user_id,order_date,greeting_text,hours,minutes,price,payment_method,status,shop_id) VALUES (?,?,?,?,?,?,?,0,?);";
		String s2 = "select id from orders where user_id = ? and shop_id=? order by id desc limit 1";
		String s3 = "update cart set order_id=?,order_date=now() where user_id=? and order_id=0 and shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, order.getUser().getId());
				ps.setDate(2,  Date.valueOf(order.getDate()));
				ps.setString(3, order.getGreeting());
				ps.setInt(4, order.getHours());
				ps.setInt(5, order.getMinutes());
				ps.setFloat(6, order.getPrice());
				ps.setInt(7,order.getPaymentMethod());
				ps.setInt(8, ServerController.shop.getId());
				ps.executeUpdate(); 

				

				try {
						ps2 = (PreparedStatement) conn.prepareStatement(s2);
						ps2.setInt(1, order.getUser().getId());
						ps2.setInt(2, ServerController.shop.getId());
						rs2 = ps2.executeQuery();
						
						if(rs2.next())
						{
							ps2 = (PreparedStatement) conn.prepareStatement(s3);
							ps2.setInt(1, rs2.getInt("id"));
							ps2.setInt(2, order.getUser().getId());
							ps2.setInt(3, ServerController.shop.getId());
							ps2.executeUpdate();
						}

						// update products in cart
						
						client.sendToClient(sr); // send messeage to client
						
					}
				catch (Exception e)
				{
					
				}
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
 * get list of orders from database
 */
public static void getMyOrdersHistory(Connection conn,  ConnectionToClient client,User user) throws SQLException {
	PreparedStatement ps;
	ResultSet rs; 
	/**
	String s1 = "select orders.id as oid,orders.order_date,orders.greeting_text as gt,orders.hours,orders.minutes,orders.price,orders.payment_method"
			+ ",products.pname,products.ptype,products.price,products.img,products.product_ID,products.id as pid"
			+ " from orders,products where orders.user_id = ? and orders.product_id=products.id";
			*/
	String s1 = "select * from orders where user_id = ? and shop_id=?";
	try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, user.getId());
			ps.setInt(2, ServerController.shop.getId());
			rs = ps.executeQuery();
			ArrayList<Order> orders = new ArrayList<Order>();
			
			while ( rs.next() )
			{
				
				
				//create order
				
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setGreeting(rs.getString("greeting_text"));
				order.setHours(rs.getInt("hours"));
				order.setMinutes(rs.getInt("minutes"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				order.setPrice(rs.getFloat("price")); 
				order.setUser(user);
				//date
				Date d = rs.getDate("order_date");
				if(d!= null) 
					order.setDate(d.toLocalDate());
				//status
				if(rs.getInt("status") == 0)
				{
					order.setStatus("pending");
				}
				else
				{
					order.setStatus("canceled");
				}
				
				
				orders.add(order);
			}
			
			ServerResponse sr = new ServerResponse(); // create server response
			sr.setAction(Actions.GetMyOrdersHistory);
			sr.setValue(orders);
			
			client.sendToClient(sr); // send messeage to client
		}
	catch (Exception e)
	{
		// TODO: handle exception
		e.printStackTrace();
	}
}
/**
 * change order status to canceled
 */
public static void CancelOrder(Connection conn,  ConnectionToClient client,Order o) throws SQLException {
	ServerResponse sr = new ServerResponse(); // create server response
	sr.setAction(Actions.updateConclusion);
	PreparedStatement ps;
	String s1 = "update orders set status=1 where id=?";
	try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, o.getId());
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
