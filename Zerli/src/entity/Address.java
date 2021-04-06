package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Address implements Serializable {
	private int id;
	private String city;
	private String street;
	private int number;
	
	public Address()
	{
		
	}
	
	public Address(int id,String city,String street,int number)
	{
		this.setId(id);
		this.setCity(city);
		this.setStreet(street);
		this.setNumber(number);
	}
	
	public Address(String city,String street,int number)
	{
		this.setCity(city);
		this.setStreet(street);
		this.setNumber(number);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
