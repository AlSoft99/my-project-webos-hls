package com.entity;

public class DeskEntity {
	private String number;//桌号
	private String order;//点单状态(已点单, 未点单)
	private String owner;//点单员
	private String numberColor;//桌子状态颜色
	private String orderColor;//订单状态颜色
	private String ownerColor;//点单员颜色
	private String deskStatus;//桌子状态(预订,未开台,已开台)
	private String orderStatue;//点单状态(1:已点单, 0:未点单)
	
	public String getDeskStatus() {
		return deskStatus;
	}
	public void setDeskStatus(String deskStatus) {
		this.deskStatus = deskStatus;
	}
	public String getOrderStatue() {
		return orderStatue;
	}
	public void setOrderStatue(String orderStatue) {
		this.orderStatue = orderStatue;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOrder() {
		return order;
	}
	public String getNumberColor() {
		return numberColor;
	}
	public void setNumberColor(String numberColor) {
		this.numberColor = numberColor;
	}
	public String getOrderColor() {
		return orderColor;
	}
	public void setOrderColor(String orderColor) {
		this.orderColor = orderColor;
	}
	public String getOwnerColor() {
		return ownerColor;
	}
	public void setOwnerColor(String ownerColor) {
		this.ownerColor = ownerColor;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
}