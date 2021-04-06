package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Refund implements Serializable {
	private int id;
	private Order order;
	private float refund;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public float getRefund() {
		return refund;
	}
	public void setRefund(float refund) {
		this.refund = refund;
	}
}
