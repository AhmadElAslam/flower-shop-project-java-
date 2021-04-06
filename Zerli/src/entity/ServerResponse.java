package entity;
import java.io.Serializable;

import enums.Actions;;

public class ServerResponse implements Serializable {
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
	private Actions answer;
	private Object value;
	
	public ServerResponse()
	{
		
	}
	public ServerResponse(Actions act)
	{
		this.action = act;
		value = null;
	}
	
	public ServerResponse(Actions act,Object val)
	{
		action = act;
		value = val;
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
	public Actions getAnswer() {
		return answer;
	}
	public void setAnswer(Actions answer) {
		this.answer = answer;
	}
	
	
	
}
