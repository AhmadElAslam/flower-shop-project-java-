package gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import entity.ImgFile;
import entity.Product;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * insert new product
 */
public class UpdateCatalogController extends GUIcontroller  {
	
	public static UpdateCatalogController last;
	private ObservableList<Product> ObserProducts;
	@FXML
	private TableView<Product> ProductsTable = new TableView<Product>(); // table of products
	
	
	@FXML
	private TextField txtFieldPname;
	@FXML
	private TextField txtFieldPtype;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtPid;
	
	@FXML
	private Button btnAdd;
	@FXML
	private Button selectImg;
	
	
	File file; // Image file to upload
	
	
		/**
		 *  select image
		 */
		@FXML
		public void onSelectImg(ActionEvent event) throws Exception {
			FileChooser fileChooser = new FileChooser();
			file = fileChooser.showOpenDialog(getCurrentStage());
			selectImg.setText("Image selected");
		}
		
		/**
		 *  add product
		 */
		@FXML
		public void onBtnAddClicked(ActionEvent event) throws Exception {
			try {
				System.out.println("xxx");
			if (file != null) {
				System.out.println("yyy");
			// get data from form
			String pname = txtFieldPname.getText();
			String ptype = txtFieldPtype.getText();
			ImgFile imgf = getImgFile(file.toString());
			Float price = Float.parseFloat(txtPrice.getText());
			String productID = txtPid.getText();
			
			// create product
			Product p = new Product(pname,ptype);
			p.setImgf(imgf);
			p.setPrice(price);
			p.setProductId(productID);
			
			// send request to server
			sendRequestToServer(Actions.AddProduct,p);
			sendRequestToServer(Actions.GetProducts);
			System.out.println("zzz");
			}
			else
			{
				// no picture selected
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		/**
		 *  delete selected row from database
		 */
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {
			// get selected item
			Product product = ProductsTable.getSelectionModel().getSelectedItem();
			if(product!= null)
			{
    			sendRequestToServer(Actions.DeleteProduct,product);
    			sendRequestToServer(Actions.GetProducts); // refresh table
				
			}
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillProductsInTable(ArrayList<Product> products)
		{
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
				TableColumn<Product, String> typeCol = new TableColumn<Product, String>("Type");
				
				
				
				//add data to columns
		        nameCol.setCellValueFactory(
		        	    new PropertyValueFactory<Product,String>("productName")
		        	); 
		        
		        TableColumn<Product, Float> PriceCol = new TableColumn<Product, Float>("price");
		        PriceCol.setCellValueFactory(  new PropertyValueFactory<Product,Float>("price") );
		        PriceCol.setCellFactory(TextFieldTableCell.<Product, Float>forTableColumn(new FloatStringConverter()));
		        PriceCol.setOnEditCommit(
			            new EventHandler<CellEditEvent<Product, Float>>() {
			                @Override
			                public void handle(CellEditEvent<Product, Float> t) {
			                	// show new name in column
			                    ((Product) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow())
			                        ).setPrice(t.getNewValue());
			                    
			                    //update new name to database
			                    Float newPrice = t.getNewValue();
			                    Product productToUpdate = (Product) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow());
			                    productToUpdate.setPrice(newPrice);
			                    
			                    //send request to server
			        			sendRequestToServer(Actions.UpdateProduct,productToUpdate);
			                }
			            }
			        );
		        
		        //update name
		        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		        nameCol.setOnEditCommit(
		            new EventHandler<CellEditEvent<Product, String>>() {
		                @Override
		                public void handle(CellEditEvent<Product, String> t) {
		                	// show new name in column
		                    ((Product) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setProductName(t.getNewValue());
		                    
		                    //update new name to database
		                    String newName = t.getNewValue();
		                    Product productToUpdate = (Product) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow());
		                    productToUpdate.setProductName(newName);
		                    
		                    //send request to server
		        			sendRequestToServer(Actions.UpdateProduct,productToUpdate);
		                }
		            }
		        );
		        
		        TableColumn<Product, Integer> stockCol = new TableColumn<Product, Integer>("Stock");
		        stockCol.setCellValueFactory(  new PropertyValueFactory<Product,Integer>("stock") );
		        stockCol.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
		        stockCol.setOnEditCommit(
		            new EventHandler<CellEditEvent<Product, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Product, Integer> t) {
		                	// show new name in column
		                    ((Product) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setStock(t.getNewValue());
		                    
		                    //update new name to database
		                    int newStock = t.getNewValue();
		                    Product productToUpdate = (Product) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow());
		                    productToUpdate.setStock(newStock);
		                    
		                    //send request to server
		        			sendRequestToServer(Actions.UpdateProduct,productToUpdate);
		                }
		            }
		        );

		        
		        typeCol.setCellValueFactory(
		        	    new PropertyValueFactory<Product,String>("productType")
		        	);
		        
		      //update type
		        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		        typeCol.setOnEditCommit(
		            new EventHandler<CellEditEvent<Product, String>>() {
		                @Override
		                public void handle(CellEditEvent<Product, String> t) {
		                	// show new name in column
		                    ((Product) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setProductName(t.getNewValue());
		                    
		                    //update new name to database
		                    String newType = t.getNewValue();
		                    Product productToUpdate = (Product) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow());
		                    productToUpdate.setProductType(newType);
		                    
		                    //send request to server
		                    sendRequestToServer(Actions.UpdateProduct,productToUpdate);
		                }
		            }
		        );
		        
		      
		        /**
		        TableColumn<Product, ProductImage> imageCol = new TableColumn<Product, ProductImage>("Image");
		        imageCol.setCellValueFactory(new PropertyValueFactory<Product, ProductImage>("productImg"));
		        imageCol.setPrefWidth(60);
		      */
				
		        
		        ProductsTable.setItems(ObserProducts);
		        ProductsTable.getColumns().addAll(nameCol, typeCol,PriceCol,stockCol);
			}
		}
		
		/**
		 *  select image
		 */
		public ImgFile getImgFile(String LocalfilePath)
		{
			// string IMG - path to file
			
			  String extension = "";
			  String fileName = "";

			  // get file exe( e.x "filename.jpg" ==> jpg
			  int i = LocalfilePath.lastIndexOf('.');
			  if (i >= 0) {
			      extension = LocalfilePath.substring(i+1);
			  }
			 
			
			  ImgFile msg= new ImgFile(LocalfilePath);
			  msg.setExe(extension);
			  try{

				      File newFile = new File (LocalfilePath);
				      		      
				      byte [] mybytearray  = new byte [(int)newFile.length()];
				      FileInputStream fis = new FileInputStream(newFile);
				      BufferedInputStream bis = new BufferedInputStream(fis);			  
				      
				      msg.initArray(mybytearray.length);
				      msg.setSize(mybytearray.length);
				      
				      bis.read(msg.getMybytearray(),0,mybytearray.length);		      
				    }
				catch (Exception e) {
					System.out.println("Error send (Files)msg) to Server");
				}
			  return msg;
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// getProducts from database
			sendRequestToServer(Actions.GetProducts);
			
		}
	
}
