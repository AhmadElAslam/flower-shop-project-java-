package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CreditCard implements Serializable {
	private int id;
	private String cardNumber;
	private int expMonth;
	private int expYear;
	private String cvv;
	private User user; // user that credit card belongs to
	private int authorized;
	
	public CreditCard() {
		// empty constructor
	}
	
	public CreditCard(String cardNumber,int expMonth,int expYear,String cvv,User user)
	{
		this.setCardNumber(cardNumber);
		this.setCvv(cvv);
		this.setExpMonth(expMonth);
		this.setExpYear(expYear);
		this.setUser(user);
	}
	
	public CreditCard(int id,String cardNumber,int expMonth,int expYear,String cvv,User user)
	{
		this.setId(id);
		this.setCardNumber(cardNumber);
		this.setCvv(cvv);
		this.setExpMonth(expMonth);
		this.setExpYear(expYear);
		this.setUser(user);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(int expMonth) {
		this.expMonth = expMonth;
	}
	public int getExpYear() {
		return expYear;
	}
	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public int getAuthorized() {
		return authorized;
	}

	public void setAuthorized(int authorized) {
		this.authorized = authorized;
	}
	
}
