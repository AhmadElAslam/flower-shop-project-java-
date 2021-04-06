package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ReportOrders implements Serializable {
	private ArrayList<CartProduct> cart;

	public ArrayList<CartProduct> getCart() {
		return cart;
	}

	public void setCart(ArrayList<CartProduct> cart) {
		this.cart = cart;
	}
	
	public ArrayList<String> getProductTypes(int year,int quarter) {
		/* get all product types from cart */
		ArrayList<CartProduct> cartq = new ArrayList<CartProduct>();
		if(year==0 && quarter==0)
			cartq = cart;
		else cartq = getCartByQY(year,quarter);
		
		ArrayList<String> types = new ArrayList<String>();
		for(CartProduct c: cart)
			if(!types.contains(c.getProduct_type()))
				types.add(c.getProduct_type());
		
		return types;
	}
	
	public ArrayList<CartProduct> getCartByQY(int year,int quarter) {
		// return all revenue elements from specific year and quarter
		ArrayList<CartProduct> cartByQY = new ArrayList<CartProduct>();
		for(CartProduct r: cart)
		{
			if(r.getOrderDate().getYear() == year && r.getQuarter() == quarter)
				cartByQY.add(r);
		}
			
		return cartByQY;
	}
	
	public ArrayList<Integer> GetYears()
	{
		ArrayList<Integer> years = new ArrayList<Integer>();
		for(CartProduct r: cart)
			if(!years.contains(r.getOrderDate().getYear()))
				years.add(r.getOrderDate().getYear());
		
		return years;
	}
	
	public int getNumberOfProductsByType(String type,int year,int quarter)
	{
		// set relevant array
		ArrayList<CartProduct> cartq = new ArrayList<CartProduct>();
		if(year==0 && quarter==0)
			cartq = cart;
		else cartq = getCartByQY(year,quarter);
		
		int numberProductsByType=0;
		
		for(CartProduct c: cartq)
		{
			if(c.getProduct_type()!=null)
				if(c.getProduct_type().equals(type))
					numberProductsByType++;
		}
		return numberProductsByType;
	}
}
