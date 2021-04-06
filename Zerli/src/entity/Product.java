package entity;

import java.io.Serializable;

import gui.LoginController;

@SuppressWarnings("serial")
public class Product implements Serializable {
	private int pid;
	private String productName;
	private String productType;
	private float price;
	private ImgFile imgf;
	private String productId;
	private String image; // image path
	private float dealPrice;
	private int stock;
	
	public Product(	String name, String type) {
		this.setProductName(name);
		this.setProductType(type);
	}
	
	public Product(	int id, String name, String type) {
		this.setPid(id);
		this.setProductName(name);
		this.setProductType(type);
	}
	
	public Product(int id,String name, String type,String img) {
		this.setPid(id);
		this.setProductName(name);
		this.setProductType(type);
		//image
		/*
		 * image of javafx can't be serializable
		Image image = new Image("C:\\Users\\danik\\Desktop\\download.jpg");
		ImageView imageView = new ImageView(image);

		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		this.setImage(imageView);
		*/
		this.setImage(img);
	}
	

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public ImgFile getImgf() {
		return imgf;
	}

	public void setImgf(ImgFile imgf) {
		this.imgf = imgf;
	}

	public float getPrice() {
		return setSubscription(price);
	}
	
	public float getPriceA() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image; 
	}
	
	public String toString()
	{
		return this.productName;
	}

	public float getDealPrice() {
		return setSubscription(dealPrice);
	}

	public void setDealPrice(float dealPrice) {
		this.dealPrice = dealPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public float setSubscription(float price) {
		if(price!=0) {
			if(LoginController.myUser.getAuthorized()==2)
			{
				price = price*95/100;
			}
			if(LoginController.myUser.getAuthorized()==3)
			{
				price = price*80/100;
			}
		}
		return price;
	}
	
}
