package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import entity.CartProduct;
import entity.ReportOrders;
import entity.ReportRevenue;
import entity.Request;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/**
 *  2 screens order report for netwrok manager
 */
public class ReportOrders2Controller extends GUIcontroller {
	public static ReportOrders2Controller last;
	
	@FXML private ComboBox<Integer> cmbq1;
	@FXML private ComboBox<Integer> cmbYear1;
	
	@FXML private ComboBox<Integer> cmbq2;
	@FXML private ComboBox<Integer> cmbYear2;
	
	@FXML private BarChart<String, Integer> barChart1;
	@FXML private BarChart<String, Integer> barChart2;
	@FXML NumberAxis yAxis;
	@FXML CategoryAxis xAxis;
	
	@FXML private Text shopTxt1;
	@FXML private Text shopTxt2;
	
	public ReportOrders report1; // this report
	public ReportOrders report2; // this report
	

		
		
		@FXML
		public void change1(ActionEvent event) throws Exception {
			selectYearAndQuarter1();
		}
		
		@FXML
		public void change2(ActionEvent event) throws Exception {
			selectYearAndQuarter2();
		}
		
		public void selectYearAndQuarter1()
		{
			// on select year and quarter
			boolean isq = cmbq1.getSelectionModel().isEmpty();
			boolean isy = cmbYear1.getSelectionModel().isEmpty();
			if(isy==false && isq==false) // checks if 2 combobox selected
			{
				int quarter = this.cmbq1.getSelectionModel().getSelectedItem(); 
				int year = this.cmbYear1.getSelectionModel().getSelectedItem(); 
				

				this.fillBarChart1(report1, year, quarter);
			}
			
		}
		
		public void selectYearAndQuarter2()
		{
			boolean isq = cmbq2.getSelectionModel().isEmpty();
			boolean isy = cmbYear2.getSelectionModel().isEmpty();
			if(isy==false && isq==false) // checks if 2 combobox selected
			{
				int quarter = this.cmbq2.getSelectionModel().getSelectedItem(); 
				int year = this.cmbYear2.getSelectionModel().getSelectedItem(); 
				

				this.fillBarChart2(report2, year, quarter);
			}
		}
		
		
		public void fillBarChart1(ReportOrders report,int year,int quarter)
		{
			
			// init bar chart
			barChart1.setAnimated(true);
			barChart1.setTitle("chart");
	        xAxis.setLabel("types");       
	        yAxis.setLabel("money");
			barChart1.getData().clear();
			
			for(String type: report.getProductTypes(year, quarter))
			{
				XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
				ser1.setName(type); 
				ser1.getData().add(new Data<String, Integer>("number",(int) report.getNumberOfProductsByType(type, year, quarter)));  
				barChart1.getData().addAll(ser1);
			}
	        
	        
		}
		
		public void fillBarChart2(ReportOrders report,int year,int quarter)
		{
			
			// init bar chart
			barChart2.setAnimated(true);
			barChart2.setTitle("chart");
	        xAxis.setLabel("types");       
	        yAxis.setLabel("money");
			barChart2.getData().clear();
			
			for(String type: report.getProductTypes(year, quarter))
			{
				XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
				ser1.setName(type); 
				ser1.getData().add(new Data<String, Integer>("number",(int) report.getNumberOfProductsByType(type, year, quarter)));  
				barChart2.getData().addAll(ser1);
			}
	        
	        
		}
		
		public void fillComboYears1(ArrayList<Integer> years) {
			ObservableList<Integer> obser = FXCollections.observableArrayList(years);
			this.cmbYear1.setItems(obser);
		}

		public void fillComboYears2(ArrayList<Integer> years) {
			ObservableList<Integer> obser = FXCollections.observableArrayList(years);
			this.cmbYear2.setItems(obser);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
		
		sendRequestToServer(Actions.GetCartOrdersShop1,null,MainMenuNetworkManager.shop1);	
		sendRequestToServer(Actions.GetCartOrdersShop2,null,MainMenuNetworkManager.shop2);	
		
		// add quarters to combo
		ArrayList<Integer> quarters = new ArrayList<Integer>();
		quarters.add(1);
		quarters.add(2);
		quarters.add(3);
		quarters.add(4);
		ObservableList obser = FXCollections.observableArrayList(quarters);
		this.cmbq1.setItems(obser);
		this.cmbq2.setItems(obser);
		
		// set shops names
		shopTxt1.setText(MainMenuNetworkManager.shop1.getShopName());
		shopTxt2.setText(MainMenuNetworkManager.shop2.getShopName());
		}

}
