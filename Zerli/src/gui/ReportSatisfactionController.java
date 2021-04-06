package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;


import entity.ReportRevenue;
import entity.ReportSatisfaction;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ReportSatisfactionController extends GUIcontroller {
	public static ReportSatisfactionController last;
	private ObservableList<SurveyResults> Obser;
	@FXML
	private TableView<SurveyResults> table = new TableView<SurveyResults>(); // table of products
	@FXML private ComboBox<Integer> cmbq;
	@FXML private ComboBox<Integer> cmbYear;
	
	@FXML private BarChart<String, Integer> barChart;
	@FXML NumberAxis yAxis;
	@FXML CategoryAxis xAxis;
	
	
	@FXML private Text sumEarning;
	@FXML private Text dateTXT;
	@FXML private TextField txtSearch;
	
	public ReportSatisfaction report; // this report
	
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<SurveyResults> surveys)
		{
			/**
			 * This function fill the table with data in ArrayList 
			 */
			
			if(Obser != null) {
				// if table alredy populate
				/**Dani: didn't find better solution */
				Obser.removeAll(Obser);
				for(SurveyResults c: surveys)
				{
					Obser.add(c);
				}
				
			}
			else
			{
				
				//casting ArrayList to ObservableList
				Obser = FXCollections.observableArrayList(surveys);
				
				// defining table columns
				TableColumn<SurveyResults, LocalDate> cdateCol = new TableColumn<SurveyResults, LocalDate>("Survey date");
				
				//add data to columns
				cdateCol.setCellValueFactory( new PropertyValueFactory<SurveyResults, LocalDate>("date"));
				
				TableColumn<SurveyResults, Integer> q1Col = new TableColumn<SurveyResults, Integer>("q1");
				q1Col.setCellValueFactory( new PropertyValueFactory<SurveyResults, Integer>("q1"));
				TableColumn<SurveyResults, Integer> q2Col = new TableColumn<SurveyResults, Integer>("q2");
				q2Col.setCellValueFactory( new PropertyValueFactory<SurveyResults, Integer>("q2"));
				TableColumn<SurveyResults, Integer> q3Col = new TableColumn<SurveyResults, Integer>("q3");
				q3Col.setCellValueFactory( new PropertyValueFactory<SurveyResults, Integer>("q3"));
				TableColumn<SurveyResults, Integer> q4Col = new TableColumn<SurveyResults, Integer>("q4");
				q4Col.setCellValueFactory( new PropertyValueFactory<SurveyResults, Integer>("q4"));
				TableColumn<SurveyResults, Integer> q5Col = new TableColumn<SurveyResults, Integer>("q5");
				q5Col.setCellValueFactory( new PropertyValueFactory<SurveyResults, Integer>("q5"));
				TableColumn<SurveyResults, Integer> q6Col = new TableColumn<SurveyResults, Integer>("q6");
				q6Col.setCellValueFactory( new PropertyValueFactory<SurveyResults, Integer>("q6"));
				
				
				
		        
		    
				table.setItems(Obser);
				table.getColumns().addAll(cdateCol,q1Col,q2Col,q3Col,q4Col,q5Col,q6Col);

		        
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
				
				this.fillTable(report.getSatisfactionResultsByQY(year, quarter));
				this.fillBarChart(report, year, quarter);
			}
			
		}
		
		
		public void fillBarChart(ReportSatisfaction report,int year,int quarter)
		{
			Map<String, Integer> map = report.getSatisfactionBySurvey(year, quarter);
			
			// init bar chart
			barChart.setAnimated(true);
			barChart.setTitle("chart");
	        xAxis.setLabel("satisfaction");       
	        yAxis.setLabel("amount");
			barChart.getData().clear();
			
			for (Map.Entry<String, Integer> entry : map.entrySet())
			{
				XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
				ser1.setName(entry.getKey()); 
				ser1.getData().add(new Data<String, Integer>("amount",(int) entry.getValue()));  
				
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			    barChart.getData().addAll(ser1);
			}
		}
		
		public void fillComboYears(ArrayList<Integer> years) {
			ObservableList<Integer> obser = FXCollections.observableArrayList(years);
			this.cmbYear.setItems(obser);
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
	        
	     
			if(LoginController.myUser.getPermissions()==6)
			{
				// network manager
				sendRequestToServer(Actions.GetSatisfactionReport,null,MainMenuNetworkManager.shop1);
			}
			else
			sendRequestToServer(Actions.GetSatisfactionReport);
		
		// add quarters to combo
		ArrayList<Integer> quarters = new ArrayList<Integer>();
		quarters.add(1);
		quarters.add(2);
		quarters.add(3);
		quarters.add(4);
		ObservableList<Integer> obser = FXCollections.observableArrayList(quarters);
		this.cmbq.setItems(obser);
		
		}
}
