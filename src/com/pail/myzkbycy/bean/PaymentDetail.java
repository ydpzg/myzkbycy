package com.pail.myzkbycy.bean;

public class PaymentDetail {
	private String id;
	private String begin;
	private String end;
	private String time;
	private String number;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PaymentDetail [id=" + id + ", begin=" + begin + ", end=" + end
				+ ", time=" + time + ", number=" + number + "]";
	}

}
