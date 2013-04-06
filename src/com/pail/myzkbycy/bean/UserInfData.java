package com.pail.myzkbycy.bean;

public class UserInfData extends DataPacket {
	
	private String true_name;
	private String temp_area_id;
	private String temp_qq;
	private String temp_feixin;
	private String index_place;
	private String index_name;
	private String index_address;
	private String index_middle_number;
	private String index_phone;
	private String index_set;
	private String index_price;
	private String temp_user_active;
	private String temp_user_status;
	private String temp_qu_time;
	
	public String getTrue_name() {
		return true_name;
	}
	public void setTrue_name(String user_name) {
		this.true_name = user_name;
	}
	public String getTemp_area_id() {
		return temp_area_id;
	}
	public void setTemp_area_id(String temp_area_id) {
		this.temp_area_id = temp_area_id;
	}
	public String getTemp_qq() {
		return temp_qq;
	}
	public void setTemp_qq(String temp_qq) {
		this.temp_qq = temp_qq;
	}
	public String getTemp_feixin() {
		return temp_feixin;
	}
	public void setTemp_feixin(String temp_feixin) {
		this.temp_feixin = temp_feixin;
	}
	public String getIndex_place() {
		return index_place;
	}
	public void setIndex_place(String index_place) {
		this.index_place = index_place;
	}
	public String getIndex_name() {
		return index_name;
	}
	public void setIndex_name(String index_name) {
		this.index_name = index_name;
	}
	public String getIndex_address() {
		return index_address;
	}
	public void setIndex_address(String index_address) {
		this.index_address = index_address;
	}
	public String getIndex_middle_number() {
		return index_middle_number;
	}
	public void setIndex_middle_number(String index_middle_number) {
		this.index_middle_number = index_middle_number;
	}
	public String getIndex_phone() {
		return index_phone;
	}
	public void setIndex_phone(String index_phone) {
		this.index_phone = index_phone;
	}
	public String getIndex_set() {
		return index_set;
	}
	public void setIndex_set(String index_set) {
		this.index_set = index_set;
	}
	public String getIndex_price() {
		return index_price;
	}
	public void setIndex_price(String index_price) {
		this.index_price = index_price;
	}
	public String getTemp_user_active() {
		return temp_user_active;
	}
	public void setTemp_user_active(String temp_user_active) {
		this.temp_user_active = temp_user_active;
	}
	public String getTemp_user_status() {
		return temp_user_status;
	}
	public void setTemp_user_status(String temp_user_status) {
		this.temp_user_status = temp_user_status;
	}
	public String getTemp_qu_time() {
		return temp_qu_time;
	}
	public void setTemp_qu_time(String temp_qu_time) {
		this.temp_qu_time = temp_qu_time;
	}
	@Override
	public String toString() {
		return "UserInfData [user_name=" + true_name + ", temp_area_id="
				+ temp_area_id + ", temp_qq=" + temp_qq + ", temp_feixin="
				+ temp_feixin + ", index_place=" + index_place
				+ ", index_name=" + index_name + ", index_address="
				+ index_address + ", index_middle_number="
				+ index_middle_number + ", index_phone=" + index_phone
				+ ", index_set=" + index_set + ", index_price=" + index_price
				+ ", temp_user_active=" + temp_user_active
				+ ", temp_user_status=" + temp_user_status + ", temp_qu_time="
				+ temp_qu_time + "]";
	}
}
