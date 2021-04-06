package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.Client;
import entity.ReportRevenue;
import entity.Request;
import entity.Revenue;
import entity.Survey;
import entity.User;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReportRevenueController extends GUIcontroller  {
	
	public static ReportRevenueController last;
	private ObservableList<Revenue> ObserRevenue;
	@FXML
	private TableView<Revenue> revenueTable = new TableView<Revenue>(); // table of products
	@FXML private ComboBox<Integer> cmbq;
	@FXML private ComboBox<Integer> cmbYear;
	
	@FXML private BarChart<String, Float> barChart;
	@FXML NumberAxis yAxis;
	@FXML CategoryAxis xAxis;
	
	
	@FXML private Text sumEarning;
	@FXML private Text dateTXT;
	@FXML private TextField txtSearch;
	
	public ReportRevenue report; // this report
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<Revenue> reven)
		{
			/**
			 * This function fill the table with data in ArrayList 
			 */
			
			if(ObserRevenue != null) {
				// if table alredy populate
				/**Dani: didn't find better solution */
				ObserRevenue.removeAll(ObserRevenue);
				for(Revenue r: reven)
				{
					ObserRevenue.add(r);
				}
				
			}
			else
			{
				revenueTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserRevenue = FXCollections.observableArrayList(reven);
				
				// defining table columns
				TableColumn<Revenue, Integer> idCol = new TableColumn<Revenue, Integer>("Date");
				
				//add data to columns
				idCol.setCellValueFactory( new PropertyValueFactory<Revenue, Integer>("date"));

				TableColumn<Revenue, String> typeCol = new TableColumn<Revenue, String>("Type");
				typeCol.setCellValueFactory( new PropertyValueFactory<Revenue, String>("type"));
				
				TableColumn<Revenue, Float> moneyCol = new TableColumn<Revenue, Float>("Money");
				moneyCol.setCellValueFactory( new PropertyValueFactory<Revenue, Float>("money"));
		        
		        
		    
				revenueTable.setItems(ObserRevenue);
		        revenueTable.getColumns().addAll(idCol, typeCol,moneyCol);

		        
			}
		}
		
		public void TextNames(ReportRevenue rp,int year,int quarter)
		{
			// change Text of sum earnings
			sumEarning.setText("Sum earnings: " +Float.toString(rp.calculateSumEarnings(year,quarter)));
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
				this.fillTable(report.getMoneyByQY(year, quarter));
				fillBarChart(report,year,quarter);
				TextNames(report,year,quarter);
			}
			
		}
		
		
		public void fillBarChart(ReportRevenue report,int year,int quarter)
		{
			
			float order = report.calculateSumOrders(year,quarter);
			float comopensate = report.calculateSumCompensate(year,quarter);
			float refunds = report.calculateSumrefunds(year,quarter);
			
			barChart.setAnimated(true);
			barChart.setTitle("chart");
	        xAxis.setLabel("types");       
	        yAxis.setLabel("money");
			barChart.getData().clear();
			//barChart.layout();
			XYChart.Series<String, Float> ser1 = new Series<String, Float>();
			XYChart.Series<String, Float> ser2 = new Series<String, Float>();
			XYChart.Series<String, Float> ser3 = new Series<String, Float>();
			
			ser1.setName("orders");     
			ser2.setName("refunds");
			ser3.setName("compensataion");
	         
	        ser1.getData().add(new Data<String, Float>("money",(float) order));  
	        ser2.getData().add(new Data<String, Float>("money", (float)comopensate ));
	        ser3.getData().add(new Data<String, Float>("money", (float)refunds)); 
	        barChart.getData().addAll(ser1,ser2,ser3);
	        
		}
		
		public void fillComboYears(ArrayList<Integer> years) {
			ObservableList<Integer> obser = FXCollections.observableArrayList(years);
			this.cmbYear.setItems(obser);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			
			barChart.setAnimated(true);
			barChart.setTitle("chart");
	        xAxis.setLabel("types");       
	        yAxis.setLabel("money");
	        //report.calculateSumOrders()
	       // report.calculateSumrefunds()
	        //report.calculateSumCompensate()
	        XYChart.Series<String, Float> series1 = new Series<String, Float>();
	    	XYChart.Series<String, Float> series2 = new Series<String, Float>();
	    	XYChart.Series<String, Float> series3 = new Series<String, Float>();
	    	
	        series1.setName("orders");       
	        series1.getData().add(new XYChart.Data("money", 0));     
	        
	        
	        series2.setName("refunds");
	        series2.getData().add(new XYChart.Data("money", 0));
	        
	        
	        series3.setName("compensataion");
	        series3.getData().add(new XYChart.Data("money", 0)); 
	        
	        barChart.getData().addAll(series1,series2,series3);
	        
	     
	        if(LoginController.myUser.getPermissions()==6)
			{
				// network manager
				sendRequestToServer(Actions.GetRevenue,null,MainMenuNetworkManager.shop1);
			}
	        else
			sendRequestToServer(Actions.GetRevenue);
		
		
		
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
