package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import entity.CartProduct;
import entity.ReportOrders;
import entity.ReportRevenue;
import entity.Request;
import entity.Revenue;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *  orders report
 */
public class ReportOrdersController extends GUIcontroller {
	public static ReportOrdersController last;
	private ObservableList<CartProduct> Obser;
	@FXML
	private TableView<CartProduct> table = new TableView<CartProduct>(); // table of products
	@FXML private ComboBox<Integer> cmbq;
	@FXML private ComboBox<Integer> cmbYear;
	
	@FXML private BarChart<String, Integer> barChart;
	@FXML NumberAxis yAxis;
	@FXML CategoryAxis xAxis;
	
	
	@FXML private Text sumEarning;
	@FXML private Text dateTXT;
	@FXML private TextField txtSearch;
	
	public ReportOrders report; // this report
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<CartProduct> cart)
		{
			/**
			 * This function fill the table with data in ArrayList 
			 */
			
			if(Obser != null) {
				// if table alredy populate
				/**Dani: didn't find better solution */
				Obser.removeAll(Obser);
				for(CartProduct c: cart)
				{
					Obser.add(c);
				}
				
			}
			else
			{
				
				//casting ArrayList to ObservableList
				Obser = FXCollections.observableArrayList(cart);
				
				// defining table columns
				TableColumn<CartProduct, LocalDate> idCol = new TableColumn<CartProduct, LocalDate>("Date");
				
				//add data to columns
				idCol.setCellValueFactory( new PropertyValueFactory<CartProduct, LocalDate>("orderDate"));
				
				TableColumn<CartProduct, String> typeCol = new TableColumn<CartProduct, String>("Product type");
				typeCol.setCellValueFactory( new PropertyValueFactory<CartProduct, String>("product_type"));
		        
		        
		    
				table.setItems(Obser);
				table.getColumns().addAll(idCol, typeCol);

		        
			}
		}
		
		public void TextNames(ReportRevenue rp,int year,int quarter)
		{
			// change Text of sum earnings
			
		}
		
		@FXML
		public void changeYear(ActionEvent event) throws Exception {
			selectYearAndQuarter();
		}
		
		@FXML
		public void changeQ(ActionEvent event) throws Exception {
			selectYearAndQuarter();
		}
		
		public void selectYearAndQuarter()
		{
			// on select year and quarter
			boolean isq = cmbq.getSelectionModel().isEmpty();
			boolean isy = cmbYear.getSelectionModel().isEmpty();
			if(isy==false && isq==false) // checks if 2 combobox selected
			{
				int quarter = this.cmbq.getSelectionModel().getSelectedItem(); 
				int year = this.cmbYear.getSelectionModel().getSelectedItem(); 
				
				this.fillTable(report.getCartByQY(year, quarter));
				this.fillBarChart(report, year, quarter);
			}
			
		}
		
		
		public void fillBarChart(ReportOrders report,int year,int quarter)
		{
			
			// init bar chart
			barChart.setAnimated(true);
			barChart.setTitle("chart");
	        xAxis.setLabel("types");       
	        yAxis.setLabel("money");
			barChart.getData().clear();
			
			for(String type: report.getProductTypes(year, quarter))
			{
				XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
				ser1.setName(type); 
				ser1.getData().add(new Data<String, Integer>("number",(int) report.getNumberOfProductsByType(type, year, quarter)));  
				barChart.getData().addAll(ser1);
			}
	        
	        
		}
		
		public void fillComboYears(ArrayList<Integer> years) {
			ObservableList<Integer> obser = FXCollections.observableArrayList(years);
			this.cmbYear.setItems(obser);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
	     
			if(LoginController.myUser.getPermissions()==6)
			{
				// network manager
				sendRequestToServer(Actions.GetCartOrders,null,MainMenuNetworkManager.shop1);
			}
	        else
	        	sendRequestToServer(Actions.GetCartOrders);
		
		// add quarters to combo
		ArrayList<Integer> quarters = new ArrayList<Integer>();
		quarters.add(1);
		quarters.add(2);
		quarters.add(3);
		quarters.add(4);
		ObservableList obser = FXCollections.observableArrayList(quarters);
		this.cmbq.setItems(obser);
		
		}
}
