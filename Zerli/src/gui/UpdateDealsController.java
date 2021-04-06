package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Deal;
import entity.Product;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * create new deal
 */
public class UpdateDealsController extends GUIcontroller  {
	
	public static UpdateDealsController last;
	private ObservableList<Deal> ObserDeal;
	@FXML private TableView<Deal> DealsTable = new TableView<Deal>(); // table of products
	
	@FXML private ComboBox<Product> cmbProduct;
	@FXML private ComboBox<Integer> cmbDiscount;
	
		
		@FXML
		public void onbtnAdd(ActionEvent event) throws Exception {
			// add deal
			Product p = this.cmbProduct.getSelectionModel().getSelectedItem();
			int d = this.cmbDiscount.getSelectionModel().getSelectedItem();
			
			Deal deal = new Deal();
			deal.setPercent(d);
			deal.setProductId(p.getPid());
			
			sendRequestToServer(Actions.AddDeal,deal);
			sendRequestToServer(Actions.GetDeals);
		}
		
		/**
		 *  delete selected row from database
		 */
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {
			// get selected item
			Deal deal = DealsTable.getSelectionModel().getSelectedItem();
			if(deal!= null)
			{
				sendRequestToServer(Actions.DeleteDeal,deal);
				sendRequestToServer(Actions.GetDeals);
			}
		}
		
		
		/**
		 *  fills the table with data in ArrayList 
		 */
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<Deal> deals)
		{
			if(ObserDeal != null) {
				// if table alredy populate
				//Dani: didn't find better solution 
				ObserDeal.removeAll(ObserDeal);
				for(Deal d: deals)
				{
					ObserDeal.add(d);
				}
				
			}
			else
			{
				DealsTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserDeal = FXCollections.observableArrayList(deals);
				
				// defining table columns
				TableColumn<Deal, Integer> idCol = new TableColumn<Deal, Integer>("ID");
				TableColumn<Deal, Integer> percentCol = new TableColumn<Deal, Integer>("percent");
				TableColumn<Deal, Integer> productIdCol = new TableColumn<Deal, Integer>("Product ID");
				
				//add data to columns
				idCol.setCellValueFactory(new PropertyValueFactory<Deal,Integer>("id"));
				percentCol.setCellValueFactory( new PropertyValueFactory<Deal,Integer>("percent"));
				productIdCol.setCellValueFactory( new PropertyValueFactory<Deal,Integer>("productId"));
				
		        
				DealsTable.setItems(ObserDeal);
				DealsTable.getColumns().addAll(idCol, percentCol,productIdCol);
			}
		}
		
		public void fillComboProduct(ArrayList<Product> products)
		{
			ObservableList observableList = FXCollections.observableList(products);
		    this.cmbProduct.setItems(observableList);
		}
		
		public void fillComboPercent()
		{
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for(int i=1; i<100;i++)
			{
				arr.add(i);
			}
			
			ObservableList observableList = FXCollections.observableList(arr);
		    this.cmbDiscount.setItems(observableList);
		}
		
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get deals from database
			sendRequestToServer(Actions.GetDeals);
			sendRequestToServer(Actions.GetProductsDeals);
			fillComboPercent();
			
		}
	
}
