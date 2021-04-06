package entity;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2176148656112261925L;
	private int id;
	private String username;
	private String password;
	
	private String fname;
	private String lname;
	private String phone;
	private int permissions;
	private int authorized;
	private Shop shop;
	
	
	public User(String user,String pass) {
		this.setUsername(user);
		this.setPassword(pass);
		
	}
	
	public User(int uid,String user,String pass) {
		this.setId(uid);
		this.setUsername(user);
		this.setPassword(pass);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	public int getAuthorized() {
		return authorized;
	}

	public void setAuthorized(int authorized) {
		this.authorized = authorized;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public String toString()
	{
		return this.getFname() +" "+ this.getLname();
	}
}
