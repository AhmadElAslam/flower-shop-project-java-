package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Address;
import entity.Complain;
import entity.CreditCard;
import entity.CustomMadeProduct;
import entity.Deal;
import entity.Order;
import entity.Product;
import entity.Refund;
import entity.Request;
import entity.Shop;
import entity.Survey;
import entity.SurveyConclusion;
import entity.SurveyResults;
import entity.User;
import database.*;
import enums.Actions;

import ocsf.server.*;

public class ServerController extends AbstractServer {

	final public static int DEFAULT_PORT = 5551;
	
	
	private static String DBName = "zerli";
	private static String DBUserName = "root";
	private static String DBPassward = "dbapp1605";
	
	public static Shop shop;
	
	public ServerController(int port) {
		super(port);
	}

	/*
	 * handleMessageFromClient connect to database and decides what to do with request
	 */
	/**
	 * handleMessageFromClient connect to database and decides what to do with request
	 * Handles message from client
	 * input: messeage
	 * output: Database action
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {/* handle the error */
		}

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DBName, DBUserName, DBPassward);
		
		
			/*
			@SuppressWarnings("unchecked")
			ArrayList<String> msgArr = (ArrayList<String>) msg; // cast to array list
			System.out.println(msgArr);
			*/
			
			Request req = (Request) msg;
			shop = req.getShop();
			System.out.println("server:"+shop+shop.getId());
			
			// switch - which action to do
			if(req.getAction() == Actions.ValidLoginDataCheck)
			{
				// get products data from database
				User u = (User) req.getValue();
				LoginManagerDatabase.isValidData((com.mysql.jdbc.Connection) conn,client,u);
				
			}
			if(req.getAction() == Actions.GetProducts)
			{
				UpdateCatalogDatabase.getProducts((com.mysql.jdbc.Connection)conn, client);
			}
			if(req.getAction() == Actions.AddProduct)
			{
				UpdateCatalogDatabase.addProduct((com.mysql.jdbc.Connection)conn, client,(Product) req.getValue());
			}
			if(req.getAction() == Actions.UpdateProduct)
			{
				UpdateCatalogDatabase.updateProduct((com.mysql.jdbc.Connection)conn, client,(Product) req.getValue());
			}
			if(req.getAction() == Actions.DeleteProduct)
			{
				UpdateCatalogDatabase.deleteProduct((com.mysql.jdbc.Connection)conn, client,(Product) req.getValue());
			}
			if(req.getAction() == Actions.Logout)
			{
				LoginManagerDatabase.logout((com.mysql.jdbc.Connection)conn, client,(User) req.getValue());
			}
			if(req.getAction() == Actions.GetUsers)
			{
				UpdateUsersDatabase.getUsers((com.mysql.jdbc.Connection)conn, client,(String) req.getValue());
			}
			if(req.getAction() == Actions.DeleteUser)
			{
				UpdateUsersDatabase.deleteUser((com.mysql.jdbc.Connection)conn, client,(User) req.getValue());
			}
			if(req.getAction() == Actions.updateUser)
			{
				UpdateUsersDatabase.updateUsers((com.mysql.jdbc.Connection)conn, client,(User) req.getValue());
			}
			if(req.getAction() == Actions.GetMyAdress)
			{
				LoginManagerDatabase.getMyAdress((com.mysql.jdbc.Connection)conn, client,(User) req.getValue());
			}
			if(req.getAction() == Actions.GetMyCreditCard)
			{
				LoginManagerDatabase.GetMyCreditCard((com.mysql.jdbc.Connection)conn, client,(User) req.getValue());
			}
			if(req.getAction() == Actions.GetSurveys)
			{
				SurveyManagerDatabase.getSurveys((com.mysql.jdbc.Connection)conn, client);
			}
			if(req.getAction() == Actions.UpdateSurvey)
			{
				SurveyManagerDatabase.updateSurvey((com.mysql.jdbc.Connection)conn, client,(Survey) req.getValue());
			}
			if(req.getAction() == Actions.DeleteSurvey)
			{
				SurveyManagerDatabase.deleteSurvey((com.mysql.jdbc.Connection)conn, client,(Survey) req.getValue());
			}
			if(req.getAction() == Actions.AddSurvey)
			{
				SurveyManagerDatabase.addSurvey((com.mysql.jdbc.Connection)conn, client,(Survey) req.getValue());
			}
			if(req.getAction() == Actions.GetSurveyNames)
			{
				SurveyResultDatabase.getSurveys((com.mysql.jdbc.Connection)conn, client);
			}
			if(req.getAction() == Actions.GetSurveyData)
			{
				SurveyResultDatabase.getSurveyData((com.mysql.jdbc.Connection)conn, client, (Survey) req.getValue());
			}
			if(req.getAction() == Actions.AddSurveyResults)
			{
				SurveyResultDatabase.addSurvey((com.mysql.jdbc.Connection)conn, client, (SurveyResults) req.getValue());
			}
			if(req.getAction() == Actions.GetSurveyResults)
			{
				SurveyResultDatabase.getSurveysResults((com.mysql.jdbc.Connection)conn, client);
			}
			if(req.getAction() == Actions.DeleteSurveyResults)
			{
				SurveyResultDatabase.deleteSurvey((com.mysql.jdbc.Connection)conn, client, (SurveyResults) req.getValue());
			}
			
			if(req.getAction() == Actions.GetNotAuthorizedUsers) 
			{
				AuthorizeUsersDatabase.getUsersNotAuthorized((com.mysql.jdbc.Connection)conn, client);
			}
			if(req.getAction() == Actions.AuthorizeUser)
			{
				AuthorizeUsersDatabase.AuthorizeUser((com.mysql.jdbc.Connection)conn, client,(CreditCard)req.getValue());
			}
			if(req.getAction() == Actions.GetSurveyNamesExpert)
			{
				ServiceExpertDatabase.getSurveys((com.mysql.jdbc.Connection)conn, client);
			}
			if(req.getAction() == Actions.GetNumberOfVoters)
			{
				ServiceExpertDatabase.getSurveyNumOfVoters((com.mysql.jdbc.Connection)conn, client,(Survey) req.getValue());
			}
			if(req.getAction() == Actions.GetAvgRes)
			{
				ServiceExpertDatabase.getSurveyResultAvg((com.mysql.jdbc.Connection)conn, client, (Survey) req.getValue());
			}
			if(req.getAction() == Actions.GetConclusion)
			{
				ServiceExpertDatabase.getConclusion((com.mysql.jdbc.Connection)conn, client, (Survey) req.getValue());
			}
			if(req.getAction() == Actions.addConclusion)
			{
				ServiceExpertDatabase.addConclusion((com.mysql.jdbc.Connection)conn, client, (SurveyConclusion) req.getValue());
			}
			if(req.getAction() == Actions.updateConclusion)
			{
				ServiceExpertDatabase.updateConclusion((com.mysql.jdbc.Connection)conn, client, (SurveyConclusion) req.getValue());
			}
			if(req.getAction() == Actions.GetProductCatalog)
			{
				CatalogDatabase.getProducts((com.mysql.jdbc.Connection)conn, client,(String) req.getValue());
			} 
			if(req.getAction() == Actions.AddAddress)
			{
				ArrayList<Object> arr = (ArrayList<Object>) req.getValue();
				Address ad = (Address) arr.get(0);
				User u = (User) arr.get(1);
				UpdateMyUser.addAddress((com.mysql.jdbc.Connection)conn, client,ad,u);
			} 
			if(req.getAction() == Actions.AddCreditCard)
			{
				ArrayList<Object> arr = (ArrayList<Object>) req.getValue();
				CreditCard cc = (CreditCard) arr.get(0);
				User u = (User) arr.get(1);
				UpdateMyUser.addCreditCard((com.mysql.jdbc.Connection)conn, client,cc,u); 
			} 
			if(req.getAction() == Actions.UpdateCreditCard)
			{
				ArrayList<Object> arr = (ArrayList<Object>) req.getValue();
				CreditCard cc = (CreditCard) arr.get(0);
				User u = (User) arr.get(1);
				UpdateMyUser.updateCreditCard((com.mysql.jdbc.Connection)conn, client,cc,u);
			} 
			if(req.getAction() == Actions.UpdateAddress)
			{
				ArrayList<Object> arr = (ArrayList<Object>) req.getValue();
				Address ad = (Address) arr.get(0);
				User u = (User) arr.get(1);
				UpdateMyUser.updateAddress((com.mysql.jdbc.Connection)conn, client,ad,u);
			} 
			if(req.getAction() == Actions.buyProductFromCatalog)
			{
				orderCatalogDatabase.order((com.mysql.jdbc.Connection)conn, client, (Order) req.getValue());
			
			} 
			if(req.getAction() == Actions.GetMyOrdersHistory)
			{
				orderCatalogDatabase.getMyOrdersHistory((com.mysql.jdbc.Connection)conn, client, (User) req.getValue());
			} 
			if(req.getAction() == Actions.CancelOrder)
			{
				orderCatalogDatabase.CancelOrder((com.mysql.jdbc.Connection)conn, client, (Order) req.getValue());
			} 
			if(req.getAction() == Actions.AddRefund)
			{
				RefundDatabase.addRefund((com.mysql.jdbc.Connection)conn, client, (Refund) req.getValue());
			} 
			if(req.getAction() == Actions.GetMyCart)
			{
				CartDatabase.getMyCart((com.mysql.jdbc.Connection)conn, client, (User) req.getValue());
			} 
			if(req.getAction() == Actions.AddToCart)
			{
				/*
				 * order -> product,user -> product_id, user_id
				 */
				CartDatabase.addToCart((com.mysql.jdbc.Connection)conn, client, (Order) req.getValue());
			} 
			if(req.getAction() == Actions.GetMyCartCountItems)
			{
				CartDatabase.getCountItemsMyCart((com.mysql.jdbc.Connection)conn, client, (User) req.getValue(),Actions.GetMyCartCountItems);
			} 
			if(req.getAction() == Actions.GetMyCartCountItemsCatalog)
			{
				CartDatabase.getCountItemsMyCart((com.mysql.jdbc.Connection)conn, client, (User) req.getValue(),Actions.GetMyCartCountItemsCatalog);
			} 
			if(req.getAction() == Actions.DeleteFromCart)
			{
				CartDatabase.deleteFromCart((com.mysql.jdbc.Connection)conn, client, (Order) req.getValue());
			} 
			
			if(req.getAction() == Actions.CustomOrderData)
			{
				CustomOrderDatabase.getCustomOrderData((com.mysql.jdbc.Connection)conn, client);
			} 
			if(req.getAction() == Actions.AddCustomOrder)
			{
				CartDatabase.addToCartCustom((com.mysql.jdbc.Connection)conn, client, (CustomMadeProduct) req.getValue());
			} 
			if(req.getAction() == Actions.GetDeals)
			{
				DealsDatabase.getDeals((com.mysql.jdbc.Connection)conn, client);
			} 
			if(req.getAction() == Actions.DeleteDeal)
			{
				DealsDatabase.deleteDeal((com.mysql.jdbc.Connection)conn, client, (Deal) req.getValue());
			} 
			if(req.getAction() == Actions.AddDeal)
			{
				DealsDatabase.addDeal((com.mysql.jdbc.Connection)conn, client, (Deal) req.getValue());
			} 
			if(req.getAction() == Actions.GetProductsDeals)
			{
				DealsDatabase.getProducts((com.mysql.jdbc.Connection)conn, client);
			} 
			if(req.getAction() == Actions.GetDealsCatalog)
			{
				DealsDatabase.getDeals((com.mysql.jdbc.Connection)conn, client);
			} 
			if(req.getAction() == Actions.GetComplain)
			{
				ComplainsDatabase.getComplains((com.mysql.jdbc.Connection)conn, client);
			} 
			if(req.getAction() == Actions.AddComplain)
			{
				ComplainsDatabase.addComplain((com.mysql.jdbc.Connection)conn, client, (Complain) req.getValue());
			} 
			if(req.getAction() == Actions.DeleteComplain)
			{
				ComplainsDatabase.deleteComplain((com.mysql.jdbc.Connection)conn, client, (Complain) req.getValue());
			} 
			if(req.getAction() == Actions.GetComplainUsers)
			{
				ComplainsDatabase.getUsers((com.mysql.jdbc.Connection)conn, client);
			} 
			if(req.getAction() == Actions.Recompense)
			{
				ComplainsDatabase.pay((com.mysql.jdbc.Connection)conn, client, (Complain) req.getValue());
			} 
			if(req.getAction() == Actions.GetRevenue)
			{
				LocalDate start = null;
				LocalDate end = null;
				RevenueDatabase.getRevenue((com.mysql.jdbc.Connection)conn, client,start,end,null);
			} 
			if(req.getAction() == Actions.GetCartOrders)
			{
				CartProudctDatabase.getCart((com.mysql.jdbc.Connection)conn, client,null);
			} 
			if(req.getAction() == Actions.getComplainsReport)
			{
				ComplainsDatabase.getComplainsReport((com.mysql.jdbc.Connection)conn, client,null);
			} 
			if(req.getAction() == Actions.GetRevenueShop1)
			{
				LocalDate start = null;
				LocalDate end = null;
				RevenueDatabase.getRevenue((com.mysql.jdbc.Connection)conn, client,start,end,Actions.GetRevenueShop1);
			} 
			if(req.getAction() == Actions.GetCartOrdersShop1)
			{
				CartProudctDatabase.getCart((com.mysql.jdbc.Connection)conn, client,Actions.GetCartOrdersShop1);
			} 
			if(req.getAction() == Actions.getComplainsReportShop1)
			{
				ComplainsDatabase.getComplainsReport((com.mysql.jdbc.Connection)conn, client,Actions.getComplainsReportShop1);
			} 
			if(req.getAction() == Actions.GetRevenueShop2)
			{
				LocalDate start = null;
				LocalDate end = null;
				RevenueDatabase.getRevenue((com.mysql.jdbc.Connection)conn, client,start,end,Actions.GetRevenueShop2);
			} 
			if(req.getAction() == Actions.GetCartOrdersShop2)
			{
				CartProudctDatabase.getCart((com.mysql.jdbc.Connection)conn, client,Actions.GetCartOrdersShop2);
			} 
			if(req.getAction() == Actions.getComplainsReportShop2)
			{
				CartProudctDatabase.getCart((com.mysql.jdbc.Connection)conn, client,Actions.getComplainsReportShop2);
			} 
			if(req.getAction() == Actions.GetSatisfactionReport)
			{
				LocalDate start = null;
				LocalDate end = null;
				SurveyResultDatabase.getSatisfaction((com.mysql.jdbc.Connection)conn, client,start,end,null);
			} 
			if(req.getAction() == Actions.GetSatisfactionReportShop1)
			{
				LocalDate start = null;
				LocalDate end = null;
				SurveyResultDatabase.getSatisfaction((com.mysql.jdbc.Connection)conn, client,start,end,Actions.GetSatisfactionReportShop1);
			} 
			if(req.getAction() == Actions.GetSatisfactionReportShop2)
			{
				LocalDate start = null;
				LocalDate end = null;
				SurveyResultDatabase.getSatisfaction((com.mysql.jdbc.Connection)conn, client,start,end,Actions.GetSatisfactionReportShop2);
			} 
			
			
			
			
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}


	@Override
	protected void serverStarted() {
		System.out.println("Server listening for connections on port "
				+ getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}
	
	public static void serverDetailsUpdate(String name,String user,String pass)
	{
		ServerController.DBName = name;
		ServerController.DBUserName = user;
		ServerController.DBPassward = pass;
	}

	

}