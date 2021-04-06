package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.CustomMadeProduct;
import entity.Order;
import entity.Product;
import entity.ServerResponse;
import entity.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class CartDatabase {
	/**
	 * insert product to cart
	 * @param order data to insert
	 */
	public static void addToCart(Connection conn,  ConnectionToClient client,Order order) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddToCart);
		PreparedStatement ps;
		
		String s1 = "INSERT INTO cart (user_id,product_id,order_id,shop_id,order_date) VALUES (?,?,0,?,now());";
		
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, order.getUser().getId());
				ps.setInt(2, order.getProduct().getPid());
				ps.setInt(3, ServerController.shop.getId());
				ps.executeUpdate();

				// update products in cart
				
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
	 * insert custom product to cart
	 */
public static void addToCartCustom(Connection conn,  ConnectionToClient client,CustomMadeProduct cmp) throws SQLException {

		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddCustomOrder);
		PreparedStatement ps;
		ResultSet rs;
		PreparedStatement ps2;
		
		String s1 = "select id from products where shop_id=? and ptype=? and price<? and price>?";
		String s2 = "INSERT INTO cart (user_id,product_id,order_id,shop_id,order_date) VALUES (?,?,0,?,now());";
		
		if(!cmp.getColor().equals("all")) {
			s1=s1+ " and color=?";
		}
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				ps.setString(2, cmp.getType());
				ps.setFloat(3, cmp.getMaxPrice());
				ps.setFloat(4, cmp.getMinPrice());
				if(!cmp.getColor().equals("all")) {
					ps.setString(5, cmp.getColor());
				}
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					ps2 = (PreparedStatement) conn.prepareStatement(s2);
					ps2.setInt(1, cmp.getMyUser().getId());
					ps2.setInt(2, rs.getInt("id"));
					ps2.setInt(3, ServerController.shop.getId());
					ps2.executeUpdate();
					sr.setAnswer(Actions.CustomAdded);
					client.sendToClient(sr);
				}
				else
				{
					sr.setAnswer(Actions.CustomNotAdded);
					client.sendToClient(sr);
				}
				
				

				// update products in cart
				
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			try {
				sr.setAnswer(Actions.CustomNotAdded);
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
/**
 * get my list of products from cart
 */
	public static void getMyCart(Connection conn,  ConnectionToClient client,User myUser) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		PreparedStatement ps2;
		ResultSet rs2; 
		PreparedStatement ps3;
		ResultSet rs3; 
		/**
		String s1 = "select products.pname,products.ptype,products.price,products.img,products.product_id"
				+ " from cart,products where cart.product_id=products.id and cart.order_id=0 and cart.user_id=?";
		*/
		String s1 = "select * from cart where order_id=0 and user_id =? and shop_id=?"; // not ordered
		String s3 = "select * from deals where product_id=? and shop_id=?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, myUser.getId());
			ps.setInt(2, ServerController.shop.getId());
			rs = ps.executeQuery();
			
			ArrayList<Product> products = new ArrayList<Product>();
			while ( rs.next() )
			{
				try {
				String s2 = "select * from products where id=? and shop_id=?";
				ps2 = (PreparedStatement) conn.prepareStatement(s2);
				ps2.setInt(1, rs.getInt("product_id"));
				ps2.setInt(2, ServerController.shop.getId());
				rs2 = ps2.executeQuery();
				
				
				
					if(rs2.next()) {
					// create product
					Product product = new Product(
							rs2.getInt("id"),
							rs2.getString("pname"),
							rs2.getString("ptype")
							);
					
					
					product.setPrice(rs2.getFloat("price"));
					product.setProductId(rs2.getString("product_id"));
					product.setImage("/serverImages/"+rs2.getString("img"));
					
					product.setDealPrice(0); // Default
					
					//deals
					ps3 = (PreparedStatement) conn.prepareStatement(s3);
					ps3.setInt(1, product.getPid());
					ps3.setInt(2, ServerController.shop.getId());
					rs3 = ps3.executeQuery();
					if(rs3.next())
					{
						int percent = rs3.getInt("percent");
						product.setDealPrice(rs2.getFloat("price")-rs2.getFloat("price")*percent/100);
					}
					
					System.out.println(product);
					// add to product array
					products.add(product);
					}
				}
				catch (Exception e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
			}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetMyCart);
				sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * get number of items in my cart
	 * @param myUser - user need cart info
	 */
	public static void getCountItemsMyCart(Connection conn,  ConnectionToClient client,User myUser,Actions action) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select count(id) as c from cart where order_id=0 and user_id =? and shop_id=?"; // not ordered
		try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, myUser.getId());
			ps.setInt(2, ServerController.shop.getId());
			rs = ps.executeQuery();
			int count =0;
			if(rs.next())
			{
				count=rs.getInt("c");
				
			}
			ServerResponse sr = new ServerResponse(); // create server response
			sr.setAction(action);
			sr.setValue(count);
			
			client.sendToClient(sr); // send messeage to client
			
				
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	/**
	 * delete product from cart
	 */
	public static void deleteFromCart(Connection conn,  ConnectionToClient client,Order order) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteFromCart);
		PreparedStatement ps;
		String s1 = "delete from cart where user_id=? and product_id=? and shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, order.getUser().getId());
				ps.setInt(2, order.getProduct().getPid());
				ps.setInt(3, ServerController.shop.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
		
		}
	}

}
