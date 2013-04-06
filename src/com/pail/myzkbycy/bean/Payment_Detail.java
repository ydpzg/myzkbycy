package com.pail.myzkbycy.bean;

public class Payment_Detail extends DataPacket {

	private String payment_time;
	private String payment_account;
	private String effective_time;
	private String payment_way;
	public String getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}
	public String getPayment_account() {
		return payment_account;
	}
	public void setPayment_account(String payment_account) {
		this.payment_account = payment_account;
	}
	public String getEffective_time() {
		return effective_time;
	}
	public void setEffective_time(String effective_time) {
		this.effective_time = effective_time;
	}
	public String getPayment_way() {
		return payment_way;
	}
	public void setPayment_way(String payment_way) {
		this.payment_way = payment_way;
	}
	@Override
	public String toString() {
		return "Payment_Detail [payment_time=" + payment_time
				+ ", payment_account=" + payment_account + ", effective_time="
				+ effective_time + ", payment_way=" + payment_way + "]";
	}

}
