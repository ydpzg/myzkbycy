package com.pail.myzkbycy.bean;

public class ReserverPlantData extends DataPacket {

	private String plantFirst;
	private String plantSecond;
	private String fruit;
	public String getPlantFirst() {
		return plantFirst;
	}
	public void setPlantFirst(String plantFirst) {
		this.plantFirst = plantFirst;
	}
	public String getPlantSecond() {
		return plantSecond;
	}
	public void setPlantSecond(String plantSecond) {
		this.plantSecond = plantSecond;
	}
	public String getFruit() {
		return fruit;
	}
	public void setFruit(String fruit) {
		this.fruit = fruit;
	}
	@Override
	public String toString() {
		return "ReserverPlantData [plantFirst=" + plantFirst + ", plantSecond="
				+ plantSecond + ", fruit=" + fruit + "]";
	}

}
