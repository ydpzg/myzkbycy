package com.pail.myzkbycy.dao;

public class LSqlContants {
	
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "myDB.db"; //数据库名
	
	
	public static final String Plant_Info = "plantinfo";
	public static final String PlantInfo_CREATE = " create table plantinfo (" +
			" plantId varchar(20) primary key , " +
			" expectedTime text(20) ," + 
			" picName varchar(50) ," +
			" plantName varchar(50) , " +
			" plantContext text(5000))";
	
	
	
	public static String[] getCreateStr() {

		String[] AllCreate = new String[]{
				PlantInfo_CREATE
		};
		return AllCreate;
	}
}
