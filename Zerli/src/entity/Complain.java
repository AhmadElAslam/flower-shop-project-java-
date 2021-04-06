package entity;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Complain implements Serializable {
	/**
	 * 
	 */
	private int id;
	private User user; 
	private String desc;
	private int status;
	private float compensation=0;
	private LocalDate complainDate;
	
	public Complain()
	{
		
	}
	
	public Complain (int complainId, User user, String desc, int status, float compensation)
	{
		this.setStatus(status);
		this.setCompensation(compensation);
		this.setId(complainId);
		this.setDesc(desc);
		this.setUser(user);
	}	
	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public float getCompensation() {
		return compensation;
	}
	public void setCompensation(float compensation) {
		this.compensation = compensation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDate getComplainDate() {
		return complainDate;
	}

	public void setComplainDate(LocalDate complainDate) {
		this.complainDate = complainDate;
	}
	
	public int getQuarter()
	{
		// get quarter
		return (complainDate.getMonthValue() / 3) + 1;
	}
	
}
