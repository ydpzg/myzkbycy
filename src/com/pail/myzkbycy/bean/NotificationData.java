package com.pail.myzkbycy.bean;

public class NotificationData extends DataPacket {
	
	private String news_title;
	private String news_content;
	private String news_data;
	private String news_id;
	
	
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public String getNews_data() {
		return news_data;
	}
	public void setNews_data(String news_data) {
		this.news_data = news_data;
	}
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}

}
