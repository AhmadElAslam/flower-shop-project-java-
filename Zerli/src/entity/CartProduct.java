package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class CartProduct implements Serializable {
	/* represent product from cart */
	private LocalDate orderDate;
	private int order_id;
	private String product_type;
	
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	
	public int getQuarter()
	{
		// get quarter
		return (orderDate.getMonthValue() / 3) + 1;
	}
	
	
	
}
