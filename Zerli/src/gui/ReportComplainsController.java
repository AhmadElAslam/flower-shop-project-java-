package gui;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import entity.Complain;
import entity.ReportComplains;
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
 *  Complains report
 */
public class ReportComplainsController extends GUIcontroller {
	public static ReportComplainsController last;
	private ObservableList<Complain> Obser;
	@FXML
	private TableView<Complain> table = new TableView<Complain>(); // table of products
	@FXML private ComboBox<Integer> cmbq;
	@FXML private ComboBox<Integer> cmbYear;
	
	@FXML private BarChart<String, Integer> barChart;
	@FXML NumberAxis yAxis;
	@FXML CategoryAxis xAxis;
	
	
	@FXML private Text sumEarning;
	@FXML private Text dateTXT;
	@FXML private TextField txtSearch;
	
	public ReportComplains report; // this report	
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<Complain> complains)
		{
			/**
			 * This function fill the table with data in ArrayList 
			 */
			
			if(Obser != null) {
				// if table alredy populate
				/**Dani: didn't find better solution */
				Obser.removeAll(Obser);
				for(Complain c: complains)
				{
					Obser.add(c);
				}
				
			}
			else
			{
				
				//casting ArrayList to ObservableList
				Obser = FXCollections.observableArrayList(complains);
				
				// defining table columns
				TableColumn<Complain, LocalDate> cdateCol = new TableColumn<Complain, LocalDate>("Complain date");
				
				//add data to columns
				cdateCol.setCellValueFactory( new PropertyValueFactory<Complain, LocalDate>("complainDate"));
				
				TableColumn<Complain, String> descCol = new TableColumn<Complain, String>("Description");
				descCol.setCellValueFactory( new PropertyValueFactory<Complain, String>("desc"));
		        
		        
		    
				table.setItems(Obser);
				table.getColumns().addAll(cdateCol, descCol);

		        
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
				
				this.fillTable(report.getComplainsByQY(year, quarter));
				this.fillBarChart(report, year, quarter);
			}
			
		}
		
		
		public void fillBarChart(ReportComplains report,int year,int quarter)
		{
			// months
			String month1 = new DateFormatSymbols().getMonths()[quarter*3-3];
			String month2 = new DateFormatSymbols().getMonths()[quarter*3-2];
			String month3 = new DateFormatSymbols().getMonths()[quarter*3-1];
			
			// init bar chart
			barChart.setAnimated(true);
			barChart.setTitle("chart");
	        xAxis.setLabel("complains");       
	        yAxis.setLabel("amount");
			barChart.getData().clear();
			
			
			XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
			ser1.setName(month1); 
			ser1.getData().add(new Data<String, Integer>("amount",(int) report.getNumberOfComplainsByMonth(quarter*3-2)));  
			
			XYChart.Series<String, Integer> ser2 = new Series<String, Integer>();
			ser2.setName(month2); 
			ser2.getData().add(new Data<String, Integer>("amount",(int) report.getNumberOfComplainsByMonth(quarter*3-1))); 
			
			
			XYChart.Series<String, Integer> ser3 = new Series<String, Integer>();
			ser3.setName(month3); 
			ser3.getData().add(new Data<String, Integer>("amount",(int) report.getNumberOfComplainsByMonth(quarter*3))); 
			barChart.getData().addAll(ser1,ser2,ser3);
			
	        
	        
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
			sendRequestToServer(Actions.getComplainsReport,null,MainMenuNetworkManager.shop1);
		}
		else
			sendRequestToServer(Actions.getComplainsReport);
		
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
