package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import entity.Deal;
import entity.Order;
import entity.Product;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/***********************************************
 * Custoemr - cart
 **********************************************/

public class CartController extends GUIcontroller  {
	
	public static CartController last;
	private ObservableList<Product> ObserProducts;
	@FXML
	private TableView<Product> ProductsTable = new TableView<Product>(); // table of products
	private ArrayList<Product> productsCart;
	private ArrayList<Deal> dealsCart;
	
	
		
		@FXML
		public void onOrder(ActionEvent event) throws Exception {
			loadFxml("orderP.fxml");
		}
	
		
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {
			/**
			 *  delete selected row from database
			 */
			
			// get selected item
			Product product = ProductsTable.getSelectionModel().getSelectedItem();
			if(product!= null)
			{
				Order o = new Order();
				o.setUser(LoginController.myUser);
				o.setProduct(product);
    			
    			sendRequestToServer(Actions.DeleteFromCart,o); // delete
    			sendRequestToServer(Actions.GetMyCart,LoginController.myUser);// refresh table
				
			}
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillProductsInTable(ArrayList<Product> products)
		{
			
			this.setProductsCart(products); // set product cart
			/**
			 * This function fill the products table with data in ArrayList products
			 * Works only for first load of tableView
			 */
			if(ObserProducts != null) {
				// if table alredy populate
				/**Dani: didn't find better solution */
				ObserProducts.removeAll(ObserProducts);
				for(Product p : products)
				{
					ObserProducts.add(p);
				}
				
			}
			else
			{
				ProductsTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserProducts = FXCollections.observableArrayList(products);
				
				// defining table columns
				TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
		        nameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("productName")); 
		        
		        TableColumn<Product, Float> priceCol = new TableColumn<Product, Float>("Price");
		        priceCol.setCellValueFactory(new PropertyValueFactory<Product,Float>("price")); 
		      
		   
		   
				
		        
		        ProductsTable.setItems(ObserProducts);
		        ProductsTable.getColumns().addAll(nameCol, priceCol);
			}
		}
		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// getProducts from database
			
			sendRequestToServer(Actions.GetMyCart,LoginController.myUser);
			
		}


		public ArrayList<Product> getProductsCart() {
			return productsCart;
		}

		public void setProductsCart(ArrayList<Product> productsCart) {
			this.productsCart = productsCart;
		}



		public ArrayList<Deal> getDealsCart() {
			return dealsCart;
		}



		public void setDealsCart(ArrayList<Deal> dealsCart) {
			this.dealsCart = dealsCart;
		}
	
}
