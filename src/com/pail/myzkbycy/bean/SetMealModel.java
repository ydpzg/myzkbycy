package com.pail.myzkbycy.bean;

public class SetMealModel {
	String temp_class;
	String temp_price;
	String temp_content;
	String temp_setid;
	public String getTemp_class() {
		return temp_class;
	}
	public void setTemp_class(String temp_class) {
		this.temp_class = temp_class;
	}
	public String getTemp_price() {
		return temp_price;
	}
	public void setTemp_price(String temp_price) {
		this.temp_price = temp_price;
	}
	public String getTemp_content() {
		return temp_content;
	}
	public void setTemp_content(String temp_content) {
		this.temp_content = temp_content;
	}
	public String getTemp_setid() {
		return temp_setid;
	}
	public void setTemp_setid(String temp_setid) {
		this.temp_setid = temp_setid;
	}
	@Override
	public String toString() {
		return "SetMealModel [temp_class=" + temp_class + ", temp_price="
				+ temp_price + ", temp_content=" + temp_content
				+ ", temp_setid=" + temp_setid + "]";
	}
	
}
