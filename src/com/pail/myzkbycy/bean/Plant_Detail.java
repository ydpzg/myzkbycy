package com.pail.myzkbycy.bean;

public class Plant_Detail extends DataPacket {

	private boolean isClick;

	private String plant_id;
	private String plant_time;
	private String plant_name;
	private String druguse_data;
	private String fertilizer_data;
	private String cook_web;

	public String getCook_web() {
		return cook_web;
	}

	public void setCook_web(String cook_web) {
		this.cook_web = cook_web;
	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}

	public String getPlant_name() {
		return plant_name;
	}

	public String getDruguse_data() {
		return druguse_data;
	}

	public void setDruguse_data(String druguse_data) {
		this.druguse_data = druguse_data;
	}

	public String getFertilizer_data() {
		return fertilizer_data;
	}

	public void setFertilizer_data(String fertilizer_data) {
		this.fertilizer_data = fertilizer_data;
	}

	public void setPlant_name(String plant_name) {
		this.plant_name = plant_name;
	}

	public String getPlant_id() {
		return plant_id;
	}

	public void setPlant_id(String plant_id) {
		this.plant_id = plant_id;
	}

	public String getPlant_time() {
		return plant_time;
	}

	public void setPlant_time(String plant_time) {
		this.plant_time = plant_time;
	}

	@Override
	public String toString() {
		return "Plant_Detail [isClick=" + isClick + ", plant_id=" + plant_id
				+ ", plant_time=" + plant_time + ", plant_name=" + plant_name
				+ ", druguse_data=" + druguse_data + ", fertilizer_data="
				+ fertilizer_data + ", cook_web=" + cook_web + "]";
	}
}
