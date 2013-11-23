package com.pail.myzkbycy.bean;

public class CaisNow {
	private String temp_zhongcai_id;
	private String temp_caiName;
	private String temp_caiId;
	private String temp_shenxia_num;

	public String getTemp_zhongcai_id() {
		return temp_zhongcai_id;
	}

	public void setTemp_zhongcai_id(String temp_zhongcai_id) {
		this.temp_zhongcai_id = temp_zhongcai_id;
	}

	public String getTemp_caiName() {
		return temp_caiName;
	}

	public void setTemp_caiName(String temp_caiName) {
		this.temp_caiName = temp_caiName;
	}

	public String getTemp_caiId() {
		return temp_caiId;
	}

	public void setTemp_caiId(String temp_caiId) {
		this.temp_caiId = temp_caiId;
	}

	public String getTemp_shenxia_num() {
		return temp_shenxia_num;
	}

	public void setTemp_shenxia_num(String temp_shenxia_num) {
		this.temp_shenxia_num = temp_shenxia_num;
	}

	@Override
	public String toString() {
		return "CaisNow [temp_zhongcai_id=" + temp_zhongcai_id
				+ ", temp_caiName=" + temp_caiName + ", temp_caiId="
				+ temp_caiId + ", temp_shenxia_num=" + temp_shenxia_num + "]";
	}

}
