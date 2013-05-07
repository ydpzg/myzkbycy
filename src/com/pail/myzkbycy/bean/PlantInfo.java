package com.pail.myzkbycy.bean;

public class PlantInfo {
	
	int plantId;
	String picName;
	String plantName;
	String expectedTime;
	String plantContext;
	String webLink;
	
	public int getPlantId() {
		return plantId;
	}

	public String getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getPlantContext() {
		return plantContext;
	}

	public void setPlantContext(String plantContext) {
		this.plantContext = plantContext;
	}

	public String getWebLink() {
		return webLink;
	}

	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}

	@Override
	public String toString() {
		return "PlantInfo [plantId=" + plantId + ", picName=" + picName
				+ ", plantName=" + plantName + ", plantContext=" + plantContext
				+ "]";
	}
	
}
