package client;

import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;

import entity.Address;
import entity.CartProduct;
import entity.Complain;
import entity.CreditCard;
import entity.Deal;
import entity.Order;
import entity.Product;
import entity.ReportComplains;
import entity.ReportOrders;
import entity.ReportRevenue;
import entity.ReportSatisfaction;
import entity.Revenue;
import entity.ServerResponse;
import entity.Survey;
import entity.SurveyResults;
import entity.User;
import enums.Actions;
import gui.AuthorizeUsersController;
import gui.CartController;
import gui.CatalogController;
import gui.CustomMadeController;
import gui.LoginController;
import gui.MainMenuCustomer;
import gui.OrderHistoryController;
import gui.ReportComplains2Controller;
import gui.ReportComplainsController;
import gui.ReportOrders2Controller;
import gui.ReportOrdersController;
import gui.ReportRevenue2Controller;
import gui.ReportRevenueController;
import gui.ReportSatisfaction2Controller;
import gui.ReportSatisfactionController;
import gui.SatisfactionSurvey;
import gui.SurveyExpertController;
import gui.SurveyResultsController;
import gui.UpdateCatalogController;
import gui.UpdateComplainsController;
import gui.UpdateDealsController;
import gui.UpdateUsersController;
import server.ConIF;

public class ClientController extends AbstractClient {
	
	ConIF clientUI;
	final public static int DEFAULT_PORT = 5551;

	public ClientController(String host, int port, ConIF clientUI)
			throws IOException {
		super(host, port);
		this.clientUI = clientUI;
		openConnection(); 
	}

	/**
	 * return message from server
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		ServerResponse sr = (ServerResponse) msg;
		// decide which action perform
		msgToGUI(sr);
				
	}

	/**
	 * XXXXXXX
	 * @param req.get(0) = action
	 * @param req.get(1) = value
	 */
	public void handleMessageFromClientUI(Object req) {
		/**
		 * 
		 */
		try {
			sendToServer(req);
		} catch (IOException e) {
			clientUI.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}
	

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
	
	/**
	 * Decide what to do
	 */
	public void msgToGUI(ServerResponse sr)
	{
		if (sr.getAction() == Actions.ValidLoginDataCheck) {
			// checked id login data correct
			LoginController loginc = (LoginController)LoginController.last;
	    	if(sr.getAnswer() == Actions.UsernameExist)
	    	{
	    		// login user
	    		LoginController.myUser = (User) sr.getValue();
	    		LoginController.myUser.setShop(LoginController.shop); // set my shop
	    		try {
					loginc.ShowLoginMessage(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	else if(sr.getAnswer() == Actions.UsernameDoesNotExist) 
	    	{
	    		// show error
	    		try {
					loginc.ShowLoginMessage(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	else if(sr.getAnswer() == Actions.AlreadyLoggedIn)
	    	{
	    		// show error
	    		try {
					loginc.ShowLoginMessage(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
		}
		
		if (sr.getAction() == Actions.GetProducts) {
			ArrayList<Product> products = (ArrayList<Product>) sr.getValue();
			UpdateCatalogController.last.fillProductsInTable(products);
		}
		if (sr.getAction() == Actions.AddProduct) {
			
		}
		if (sr.getAction() == Actions.DeleteProduct) {
			if(sr.getAnswer() == Actions.DeletedProduct)
			{
				// deleted
			}
			else
			{
				// not deleted
			}
		}
		if(sr.getAction() == Actions.GetUsers)
		{
			ArrayList<User> users = (ArrayList<User>) sr.getValue();
			UpdateUsersController.last.fillUsersInTable(users);
		}
		
		if (sr.getAction() == Actions.GetSurveys) {
			ArrayList<Survey> surveys = (ArrayList<Survey>) sr.getValue();
			SatisfactionSurvey.last.fillTable(surveys);
		}
		if (sr.getAction() == Actions.GetSurveyNames) {
			ArrayList<Survey> surveys = (ArrayList<Survey>) sr.getValue();
			SurveyResultsController.last.fillComboSurveys(surveys);
		}
		if (sr.getAction() == Actions.GetSurveyData) {
			Survey survey = (Survey) sr.getValue();
			SurveyResultsController.last.showBoxes(survey);
		}
		if (sr.getAction() == Actions.GetSurveyResults) {
			ArrayList<SurveyResults> surveys = (ArrayList<SurveyResults>) sr.getValue();
			SurveyResultsController.last.fillTable(surveys);
			
		}
		if (sr.getAction() == Actions.GetNotAuthorizedUsers) {
			ArrayList<User> users = (ArrayList<User>) sr.getValue();
			AuthorizeUsersController.last.fillTable(users);
			
		}
		
		if (sr.getAction() == Actions.GetSurveyNamesExpert) {
			ArrayList<Survey> surveys = (ArrayList<Survey>) sr.getValue();
			SurveyExpertController.last.fillComboSurveys(surveys);
		}
		if (sr.getAction() == Actions.GetNumberOfVoters) {
			SurveyExpertController.last.setNumberVoters((Integer)sr.getValue()); 
		}
		if (sr.getAction() == Actions.GetAvgRes) {
			SurveyExpertController.last.setAvgResults((SurveyResults)sr.getValue());
		}
		if (sr.getAction() == Actions.GetConclusion) {
			SurveyExpertController.last.setConclusion((String)sr.getValue());
		}
		if (sr.getAction() == Actions.GetMyAdress) {
			LoginController.myAddress = (Address)sr.getValue();
		}
		if (sr.getAction() == Actions.GetMyCreditCard) {
			LoginController.myCreditCard = (CreditCard)sr.getValue();
		}
		
		if (sr.getAction() == Actions.GetProductCatalog) {
			CatalogController.last.setCatalogProducts((ArrayList<Product>) sr.getValue());
		}
		if (sr.getAction() == Actions.GetMyOrdersHistory) {
			ArrayList<Order> orders = (ArrayList<Order>) sr.getValue();
			OrderHistoryController.last.fillTable(orders); 
			
		}
	
		if (sr.getAction() == Actions.GetMyCart) {
			ArrayList<Product> products = (ArrayList<Product>) sr.getValue();
			CartController.last.fillProductsInTable(products);
		}
		
		if (sr.getAction() == Actions.GetMyCartCountItems) {
			MainMenuCustomer.last.updateCountItems((int)sr.getValue());
		}
		if (sr.getAction() == Actions.GetMyCartCountItemsCatalog) {
			CatalogController.last.updateCountItems((int)sr.getValue());
		}
		if (sr.getAction() == Actions.CustomOrderData) {
			ArrayList<Object> obj = (ArrayList<Object>) sr.getValue();
			ArrayList<String> types = (ArrayList<String>) obj.get(0);
			ArrayList<String> colors = (ArrayList<String>) obj.get(1);
			Float max = (Float) obj.get(2);
			Float min = (Float) obj.get(3);
			
			colors.add("all");
			
			CustomMadeController.last.fillComboTypes(types);
			CustomMadeController.last.fillComboColors(colors);
			CustomMadeController.last.setMaxPrice(max);
			CustomMadeController.last.setMinPrice(min);
			
		}
		if (sr.getAction() == Actions.AddCustomOrder) {
			if(sr.getAnswer() == Actions.CustomAdded)
			{
				// added
				CustomMadeController.last.showAlert("Added to cart");
			}
			else if(sr.getAnswer() == Actions.CustomNotAdded)
			{
				// not added
				CustomMadeController.last.showAlert("No item like this");
			}
		}
		if(sr.getAction() == Actions.GetDeals)
		{
			ArrayList<Deal> deals = (ArrayList<Deal>) sr.getValue();
			UpdateDealsController.last.fillTable(deals);
		}
		if(sr.getAction() == Actions.GetProductsDeals)
		{
			ArrayList<Product> products = (ArrayList<Product>) sr.getValue();
			UpdateDealsController.last.fillComboProduct(products);
		}
		if(sr.getAction() == Actions.GetDealsCatalog)
		{
			ArrayList<Deal> deals = (ArrayList<Deal>) sr.getValue();
			CatalogController.last.setDeals(deals);
		}
		if(sr.getAction() == Actions.GetComplain)
		{
			ArrayList<Complain> complains = (ArrayList<Complain>) sr.getValue();
			complains.get(0).getDesc();
			UpdateComplainsController.last.fillTable(complains);
		}
		if(sr.getAction() == Actions.GetComplainUsers)
		{
			ArrayList<User> users = (ArrayList<User>) sr.getValue();
			UpdateComplainsController.last.fillComboUsers(users);
			
		}
		if(sr.getAction() == Actions.GetRevenue)
		{
			ReportRevenue report = (ReportRevenue) sr.getValue();
			ReportRevenueController.last.report = report;
			
			ArrayList<Revenue> reven = report.getMoney();
			ReportRevenueController.last.fillTable(reven);
			ReportRevenueController.last.TextNames(report,0,0);
			ReportRevenueController.last.fillComboYears(report.GetYears());
		}
		if(sr.getAction() == Actions.GetCartOrders)
		{
			ReportOrders report = (ReportOrders) sr.getValue();
			ReportOrdersController.last.report = report;
			ArrayList<CartProduct> cart = report.getCart();
			
			
			ReportOrdersController.last.fillTable(cart);
			ReportOrdersController.last.fillComboYears(report.GetYears());
		}
		if(sr.getAction() == Actions.getComplainsReport)
		{
			ReportComplains report = (ReportComplains) sr.getValue();
			ReportComplainsController.last.report = report;
			ArrayList<Complain> complains = report.getComplains();
			
			
			ReportComplainsController.last.fillTable(complains);
			ReportComplainsController.last.fillComboYears(report.GetYears());
			System.out.println("xx");
		}
		
		if(sr.getAction() == Actions.getComplainsReportShop1)
		{
			ReportComplains report = (ReportComplains) sr.getValue();
			ReportComplains2Controller.last.report1 = report;
			ArrayList<Complain> complains = report.getComplains();
			
			
			ReportComplains2Controller.last.fillComboYears1(report.GetYears());
		}
		if(sr.getAction() == Actions.getComplainsReportShop2)
		{
			ReportComplains report = (ReportComplains) sr.getValue();
			ReportComplains2Controller.last.report2 = report;
			ArrayList<Complain> complains = report.getComplains();
			
			
			ReportComplains2Controller.last.fillComboYears2(report.GetYears());
		}
		if(sr.getAction() == Actions.GetCartOrdersShop1)
		{
			ReportOrders report = (ReportOrders) sr.getValue();
			ReportOrders2Controller.last.report1 = report;
			ArrayList<CartProduct> cart = report.getCart();
			
			
			ReportOrders2Controller.last.fillComboYears1(report.GetYears());
		}
		if(sr.getAction() == Actions.GetCartOrdersShop2)
		{
			ReportOrders report = (ReportOrders) sr.getValue();
			ReportOrders2Controller.last.report2 = report;
			ArrayList<CartProduct> cart = report.getCart();
			
			
			ReportOrders2Controller.last.fillComboYears2(report.GetYears());
		}
		if(sr.getAction() == Actions.GetRevenueShop1)
		{
			ReportRevenue report = (ReportRevenue) sr.getValue();
			ReportRevenue2Controller.last.report1 = report;
			
			ArrayList<Revenue> reven = report.getMoney();
			ReportRevenue2Controller.last.fillComboYears1(report.GetYears());
		}
		if(sr.getAction() == Actions.GetRevenueShop2)
		{
			ReportRevenue report = (ReportRevenue) sr.getValue();
			ReportRevenue2Controller.last.report2 = report;
			
			ArrayList<Revenue> reven = report.getMoney();
			ReportRevenue2Controller.last.fillComboYears2(report.GetYears());
		}
		
		if(sr.getAction() == Actions.GetSatisfactionReport)
		{
			ReportSatisfaction report = (ReportSatisfaction) sr.getValue();
			ReportSatisfactionController.last.report = report;
			ArrayList<SurveyResults> surveys = report.getSurveyResults();
			ReportSatisfactionController.last.fillComboYears(report.GetYears());
			
			ReportSatisfactionController.last.fillTable(surveys);
			//ReportComplainsController.last.fillComboYears(report.GetYears());
		}
		if(sr.getAction() == Actions.GetSatisfactionReportShop1)
		{
			ReportSatisfaction report = (ReportSatisfaction) sr.getValue();
			ReportSatisfaction2Controller.last.report1 = report;
			
			ArrayList<SurveyResults> reven = report.getSurveyResults();
			ReportSatisfaction2Controller.last.fillComboYears1(report.GetYears());
		}
		if(sr.getAction() == Actions.GetSatisfactionReportShop2)
		{
			ReportSatisfaction report = (ReportSatisfaction) sr.getValue();
			ReportSatisfaction2Controller.last.report2 = report;
			
			ArrayList<SurveyResults> reven = report.getSurveyResults();
			ReportSatisfaction2Controller.last.fillComboYears2(report.GetYears());
		}
		
		if(sr.getAction() == Actions.GetDealsCatalog)
		{
			ArrayList<Deal> dealsCart = (ArrayList<Deal>) sr.getValue();
			CartController.last.setDealsCart(dealsCart);
		}
	}
	
}
