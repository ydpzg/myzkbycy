package com.pail.myzkbycy.dao;

import android.content.Context;


/**
 * @Description 控制数据库的初始化
 *
 */

public class DaoCenter {
	
	private static DaoCenter daoCenter ;
	private RootDao dao;
	private String[] createStrings;
	
	public static DaoCenter getInstance(){
		if(daoCenter == null ){
			daoCenter = new DaoCenter();
			
		}
		return daoCenter ;
	}

	public void initDaoCenter(Context context){
		dao = new RootDao(context);
		createStrings = LSqlContants.getCreateStr();
	}

	public void open(){
		dao.open(LSqlContants.DB_NAME, LSqlContants.getCreateStr() ,LSqlContants.Plant_Info);
	}
	
	public void close(){
		dao.close();
	}

	public RootDao getDao() {
		return dao;
	}

	public void setDao(RootDao dao) {
		this.dao = dao;
	}
	
}
