package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import entity.Deal;
import entity.Order;
import entity.Product;
import enums.Actions;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * catalog page
 */
public class CatalogController extends GUIcontroller  {
	
	public static CatalogController last;
	@FXML FlowPane flowPane;
	@FXML TextField txtSearch;
	private ArrayList<Deal> deals;
	@FXML Text numberOfItems;	// cart
	
	public static Product selectedProduct; 

		
		public void updateCountItems(int count)
		{
			this.numberOfItems.setText(count + " Items");
		}
		
		@FXML
		public void onNumberOfItems(MouseEvent event)  throws Exception {
			if(LoginController.myUser.getAuthorized()==0)
			{
				this.alertValidationWarning("Not authorized", "Shop manager must Authorize your account before you can order");
			}
			else
				loadFxml("Cart.fxml");
		}
		
		
	
		public void setCatalogProducts(ArrayList<Product> products)
		{
			
			/**
			for(Deal d: deals)
    		{
    			System.out.println(d.getPercent());
    		}
*/
			
			
			// flow pane - infinite pane to hold products
			flowPane.setPadding(new Insets(20, 0, 20, 20));
		    flowPane.setVgap(5);
		    flowPane.setHgap(5);
		    
		    flowPane.setPrefWrapLength(400);
		    flowPane.setPrefHeight(400*(products.size()/4));
		    flowPane.setOrientation(Orientation.VERTICAL);
		    

		    for (Product p: products) {
		    	GridPane gridpane = new GridPane(); // to show product data
		    	ImageView productImg = new ImageView();
		        // set Product data
		    	try {
		    		productImg.setImage(new Image("/serverImages/"+p.getImage()));
		    		productImg.setFitWidth(170);
		    		productImg.setFitHeight(170);
		    	}  catch(Throwable e) {
		            // prints stacktace for creating image
		            e.printStackTrace();
		         }
		    	
		    	Text price = new Text(Float.toString(p.getPrice()) + "$");
		    	Text dealTxt = new Text();
		    	// if there is a deal
		    	if(p.getDealPrice()!=0)
		    	{
		    		
		    		price.setStrikethrough(true);
		    		price.setText(Float.toString(p.getPrice()) + "$");
		    		dealTxt.setText(Float.toString(p.getDealPrice())+"$");
		    		dealTxt.setFill(Color.LIGHTSEAGREEN);
		    		
		    	}
		    	else {
		    		price.setText(Float.toString(p.getPrice()));
		    	}

			    Text productName = new Text(p.getProductName());
			    productName.setFont(Font.font("Microsoft JhengHei", FontWeight.BOLD, 16));
			    Button PurcasheBtn = new Button("BUY");
			    
			    PurcasheBtn.setUserData(p);
			    PurcasheBtn.setPrefSize(170, 35);
			    PurcasheBtn.setOnAction(new EventHandler<ActionEvent>() {
			        @Override public void handle(ActionEvent e) {
			        	if(LoginController.myUser.getAuthorized() == 0)
			        	{
			        		PurcasheBtn.setText("Not authorized");
			        	}
			        	else
			        	{
			        		// update selected product id
			        		Button thisBtn = (Button)e.getSource();
			        		selectedProduct = (Product)thisBtn.getUserData();
			        		
			        		Order o = new Order();
			        		o.setProduct(selectedProduct);
			        		o.setUser(LoginController.myUser);
			        		
			    			sendRequestToServer(Actions.AddToCart,o);
			    			
			    			PurcasheBtn.setText("Added");
			    			sendRequestToServer(Actions.GetMyCartCountItemsCatalog,LoginController.myUser); // cart number of items		
			        		
			        	}
			        }
			    });

			    // put information in grid
		        GridPane.setRowIndex(productName, 0);
		        GridPane.setRowIndex(productImg, 1);
		        GridPane.setRowIndex(price, 2);
		        GridPane.setRowIndex(dealTxt, 3);
		        GridPane.setRowIndex(PurcasheBtn, 4);
		        gridpane.getChildren().addAll(PurcasheBtn,productImg, productName,price,dealTxt);
		        
		        GridPane.setHalignment(productName, HPos.CENTER);
		        GridPane.setHalignment(productImg, HPos.CENTER);
		        GridPane.setHalignment(PurcasheBtn, HPos.CENTER);
		        GridPane.setHalignment(price, HPos.CENTER);
		        GridPane.setHalignment(dealTxt, HPos.CENTER);
		        
		        try {
		        	Platform.runLater(new Runnable() {
		                 @Override public void run() {
		        	flowPane.getChildren().add(gridpane);
		                 }
		             });
		    	}  catch(Throwable e) {
		            e.printStackTrace();
		         }
		        
		    	}
		}
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			sendRequestToServer(Actions.GetProductCatalog,null);
			sendRequestToServer(Actions.GetMyCartCountItemsCatalog,LoginController.myUser); // cart number of items
			
		    
		}

		public ArrayList<Deal> getDeals() {
			return deals;
		}

		public void setDeals(ArrayList<Deal> deals) {
			this.deals = deals;
		}

}
