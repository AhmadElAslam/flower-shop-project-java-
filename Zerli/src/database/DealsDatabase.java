package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.Deal;
import entity.Product;
import entity.ServerResponse;
import enums.Actions;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class DealsDatabase {
	/**
	 * get list of deals from database
	 */
	public static void getDeals(Connection conn,  ConnectionToClient client) throws SQLException {
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from deals where shop_id=?";
		
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				ArrayList<Deal> deals = new ArrayList<Deal>();
				while ( rs.next() )
				{
					// create product
					Deal deal = new Deal();
					deal.setId(rs.getInt("id"));
					deal.setProductId(rs.getInt("product_id"));
					deal.setPercent(rs.getInt("percent"));
							
					deals.add(deal);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetDeals);
				sr.setValue(deals);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * delete deal from database
	 */
	public static void deleteDeal(Connection conn,  ConnectionToClient client,Deal deal) throws SQLException {
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteDeal);
		PreparedStatement ps;
		String s1 = "delete from deals where id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, deal.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
		
		}
	}
	/**
	 * add product to database
	 */
	public static void addDeal(Connection conn,  ConnectionToClient client,Deal deal) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddDeal);
		PreparedStatement ps;
		String s1 = "INSERT INTO deals (product_id,percent,shop_id) VALUES (?,?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, deal.getProductId());
				ps.setInt(2, deal.getPercent());
				ps.setInt(3, ServerController.shop.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * get list of products from database
	 */
	public static void getProducts(Connection conn,  ConnectionToClient client) throws SQLException { 
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from products where shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, ServerController.shop.getId());
				rs = ps.executeQuery();
				ArrayList<Product> products = new ArrayList<Product>();
				while ( rs.next() )
				{
					// create product
					Product product = new Product(
							rs.getInt("id"),
							rs.getString("pname"),
							rs.getString("ptype")
							//,"serverImages/"+rs.getString("img")
							);
					
					
					product.setPrice(rs.getFloat("price"));
					product.setProductId(rs.getString("product_id"));
					product.setImage("/serverImages/"+rs.getString("img"));
					
					// add to product array
					products.add(product);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetProductsDeals);
				sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}


}
