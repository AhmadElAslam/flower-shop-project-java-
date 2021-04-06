package gui;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import client.Client;
import entity.Complain;
import entity.ReportComplains;
import entity.ReportRevenue;
import entity.ReportSatisfaction;
import entity.Request;
import entity.SurveyResults;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ReportSatisfaction2Controller extends GUIcontroller {
	public static ReportSatisfaction2Controller last;
	private ObservableList<SurveyResults> Obser;
	private ObservableList<SurveyResults> Obser2;
	
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
	
	public ReportSatisfaction report1; // this report
	public ReportSatisfaction report2; // this report
	
		
		public void TextNames(ReportRevenue rp,int year,int quarter)
		{
			// change Text of sum earnings
			
		}
		
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
		
		
		public void fillBarChart1(ReportSatisfaction report,int year,int quarter)
		{
			// months
Map<String, Integer> map = report.getSatisfactionBySurvey(year, quarter);
			
			// init bar chart
			barChart1.setAnimated(true);
			barChart1.setTitle("chart");
	        xAxis.setLabel("satisfaction");       
	        yAxis.setLabel("amount");
			barChart1.getData().clear();
			
			for (Map.Entry<String, Integer> entry : map.entrySet())
			{
				XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
				ser1.setName(entry.getKey()); 
				ser1.getData().add(new Data<String, Integer>("amount",(int) entry.getValue()));  
				
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			    barChart1.getData().addAll(ser1);
			}

			
	        
	        
		}
		
		public void fillBarChart2(ReportSatisfaction report,int year,int quarter)
		{
Map<String, Integer> map = report.getSatisfactionBySurvey(year, quarter);
			
			// init bar chart
			barChart2.setAnimated(true);
			barChart2.setTitle("chart");
	        xAxis.setLabel("satisfaction");       
	        yAxis.setLabel("amount");
			barChart2.getData().clear();
			
			for (Map.Entry<String, Integer> entry : map.entrySet())
			{
				XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
				ser1.setName(entry.getKey()); 
				ser1.getData().add(new Data<String, Integer>("amount",(int) entry.getValue()));  
				
			    System.out.println(entry.getKey() + "/" + entry.getValue());
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

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
		
		sendRequestToServer(Actions.GetSatisfactionReportShop1,null,MainMenuNetworkManager.shop1);	
		sendRequestToServer(Actions.GetSatisfactionReportShop2,null,MainMenuNetworkManager.shop2);	
		
		// add quarters to combo
		ArrayList<Integer> quarters = new ArrayList<Integer>();
		quarters.add(1);
		quarters.add(2);
		quarters.add(3);
		quarters.add(4);
		ObservableList<Integer> obser = FXCollections.observableArrayList(quarters);
		this.cmbq1.setItems(obser);
		this.cmbq2.setItems(obser);
		
		shopTxt1.setText(MainMenuNetworkManager.shop1.getShopName());
		shopTxt2.setText(MainMenuNetworkManager.shop2.getShopName());
		
		}


}
