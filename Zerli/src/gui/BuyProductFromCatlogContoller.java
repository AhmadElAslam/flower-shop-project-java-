package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Address;
import entity.CreditCard;
import entity.Order;
import entity.Product;
import entity.User;
import entity.ValidationResult;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/***********************************************
 * Customer - order confirm
 **********************************************/

public class BuyProductFromCatlogContoller extends GUIcontroller  {
	
	public static BuyProductFromCatlogContoller last;
	@FXML Text priceTXT; 
	@FXML ImageView imgView; 
	@FXML Text addressTXT; 
	@FXML Text creditCardTXT; 
	@FXML ComboBox<String> payMethod;
	@FXML ToggleButton addgc;
	@FXML DatePicker picDate;
	@FXML ComboBox<Integer> hours;
	@FXML ComboBox<Integer> minutes;
	@FXML AnchorPane greeting;
	@FXML TextArea greetingText;
	@FXML CheckBox pickCheck;
	
	private Boolean isTgPressed = false;
	
	Address address = LoginController.myAddress;
	CreditCard creditCard = LoginController.myCreditCard;
	User user = LoginController.myUser; 
	
	@FXML
	public void onBuy(ActionEvent event) throws Exception {
		// buy button clicked
		
		ValidationResult orderDate = GUIValidation.orderDate(picDate.getValue());
		
		ArrayList<String> msg = new ArrayList<>();
		boolean isValid = true;
		if(orderDate.isAnswer() == false) {
			msg.addAll(orderDate.getMsg());
			isValid = false;
		}
		
		if(isValid == false)
		{
			alertValidationWarning("Order Warning",createMsgString(msg));
		}
		else
		{
		
		LocalDate localDate = picDate.getValue();
		String greet;
		if(isTgPressed)
			greet = greetingText.getText();
		else
			greet = "";
		
		int h = Integer.parseInt(hours.getSelectionModel().getSelectedItem().toString());
		int m = Integer.parseInt(minutes.getSelectionModel().getSelectedItem().toString());
		
		int paymentMethod=0;
		
		if(payMethod.getSelectionModel().getSelectedItem().toString().equals("Cash"))
		{
			paymentMethod = 1;
		}
		if(payMethod.getSelectionModel().getSelectedItem().toString().equals("Credit card"))
		{
			paymentMethod = 2;
		}
		
		Order order = new Order();
		order.setDate(localDate);
		order.setGreeting(greet);
		order.setHours(h);
		order.setMinutes(m);
		order.setPrice(calculateTotalOrderPrice());
		order.setUser(user);
		order.setPaymentMethod(paymentMethod);
		
		sendRequestToServer(Actions.buyProductFromCatalog,order);
		
		
		loadFxml("OrdersHistory.fxml");
		}

	}
	
	
	@FXML
	public void onPicSelection(ActionEvent event) throws Exception {
		// change price
		priceTXT.setText("Total: " + calculateTotalOrderPrice());
	}
	
	@FXML
	public void onpayMethod(ActionEvent event) throws Exception {
		// pay method select 
	}
	
	@FXML
	public void onAddGrettingCard(ActionEvent event) throws Exception {
		
		if(isTgPressed)
			isTgPressed = false;
		else
			isTgPressed = true;
		
		// buy button clicked
		if(isTgPressed)
			greeting.setOpacity(1);
		else
		{
			greeting.setOpacity(0);
		}
		
	}
	

		
		public float calculateTotalOrderPrice()
		{
			/** calculate total price of shipping
			 * isShipping = false : no shipping
			 * isShipping = true : shipping
			 */
			float sum = 0;
			
			// products prices
			ArrayList<Product> products = CartController.last.getProductsCart();
			for(Product p : products)
			{
				if(p.getDealPrice() == 0) // if there is a deal
					sum+=p.getPrice();
				else
					sum+=p.getDealPrice();
			}
			
			sum+=LoginController.shop.getPriceOfShipping();
			
			// shipping
			if(pickCheck.isSelected())	
					sum-=LoginController.shop.getPriceOfShipping();
			
			return sum;			
		}
		// on change pick check
		
		
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			priceTXT.setText("Total: " + calculateTotalOrderPrice());
			
			if(address != null)
				addressTXT.setText(address.getCity() + ", " + address.getStreet() + " " + address.getNumber());
			if(creditCard != null)
				creditCardTXT.setText("******" + creditCard.getCardNumber().substring(creditCard.getCardNumber().length() - 4));
			
			
			ArrayList<Integer> hoursArray = new ArrayList<Integer>();
			for(int i=0; i<24; i++)
			{
				hoursArray.add(i);
			}
			ArrayList<Integer> minutesArray = new ArrayList<Integer>();
			for(int i=0; i<60; i++)
			{
				minutesArray.add(i);
			}
			ArrayList<String> payMeth = new ArrayList<String>();
			payMeth.add("Credit card");
			payMeth.add("Cash");
			ObservableList<Integer> obserHours = FXCollections.observableList(hoursArray);
			ObservableList<Integer> obserMinutes = FXCollections.observableList(minutesArray);
			ObservableList<String> obserPayMeth = FXCollections.observableList(payMeth);
			
			this.hours.setItems(obserHours);
			this.minutes.setItems(obserMinutes);
			this.payMethod.setItems(obserPayMeth);
		}	
}
