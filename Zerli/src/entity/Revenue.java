package entity;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Revenue implements Serializable {
	/*
	 * incomes/outcomes for reports
	 * 
	 */
	private LocalDate date;
	private String type;
	private float money;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	
	public int getQuarter()
	{
		// get quarter
		return (date.getMonthValue() / 3) + 1;
	}
	
}
