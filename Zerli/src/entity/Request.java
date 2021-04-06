package entity;
import java.io.Serializable;

import enums.Actions;
import gui.LoginController;;

public class Request implements Serializable {
	/**
	 *  Object sent to server determine what server should do
	 *  action - ENUM : what action we should do
	 *  value - parameters for action
	 */
	private static final long serialVersionUID = -1373387327000104528L;
	// all entities implements Serializable so they could be sent to server 

	/*
	 * object represent request to server
	 * instead of sending arraylist
	 */
	private Actions action;
	private Object value;
	private Shop shop;
	
	public Request()
	{
		/*this.setShop(LoginController.shop);*/
	}
	public Request(Actions act)
	{
		this.action = act;
		value = null;
		this.setShop(LoginController.shop);
	}
	
	public Request(Actions act,Object val)
	{
		action = act;
		value = val;
		this.setShop(LoginController.shop);
	}
	
	
	public Actions getAction() {
		return action;
	}
	public void setAction(Actions action) {
		this.action = action;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object values) {
		this.value = values;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	
	
}
