package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressWarnings("serial")
public class Order implements Serializable {
	private int id;
	private Product product;
	private User user;
	private LocalDate date = LocalDate.now(); // for not handling with null
	private String greeting;
	private int hours;
	private int minutes;
	private float price;
	private int paymentMethod;
	private String status;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public String toString() {
		return product.getProductName() + " price:" + price + " date:" + date;
	}
	public String getStatus() {
		return status; 
	}
	public void setStatus(String status) {
		this.status = status;
		LocalDate today = LocalDate.now();
		if(date != null)
		{
			if(status.equals("pending"))
			{
				// check if time already passed
				if(date.isBefore(today))
				{
					this.status = "delivered";
				}
				if(date.isEqual(today))
				{
					Date datex = new Date();   // given date
					Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
					calendar.setTime(datex);   // assigns calendar to given date 
					int curHour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
					int curMinute = calendar.get(Calendar.MINUTE); 
					
					
					if((curHour>this.getHours()) || (curHour==this.getHours() && curMinute>this.getMinutes()))
					{
						this.status = "delivered";
					}
	
	
				}
			}
		}
	}
	
	public float calculateRefund()
	{
		float price = this.getPrice();
		
		LocalDate today = LocalDate.now();
		if(date.isEqual(today))
		{
			Date datex = new Date();   // given date
			Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
			calendar.setTime(datex);   // assigns calendar to given date 
			
			int curHour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
			int curMinute = calendar.get(Calendar.MINUTE);
			
			//calculate difference
			int difHour = this.getHours()-curHour;
			int difMin = this.getMinutes()- curMinute;
			if(difMin<0)
			{
				difHour--;
				difMin= difMin*(-1);
			}
			
			
			if(this.getHours()-curHour > 2)
			{
				// 100%
			}
			if(difHour>0 && difHour<3)
			{
				price = (float) (price * 0.5);
			}
			if(difHour == 0)
			{
				price =0;
			}
			
			
		}
		return price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
