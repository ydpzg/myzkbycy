package com.pail.myzkbycy.dao;

public class LSqlContants {
	
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "myDB.db"; //数据库名
//	public static final String DB_PATH        = "/data/data/com.example.mytest0406/databases/";
	
	
	public static final String Plant_Info = "plantinfo";
	public static final String PlantInfo_CREATE = " create table plantinfo (" +
			" plantId varchar(20) primary key , " +
			" expectedTime text(20) ," + 
			" picName varchar(50) ," +
			
			" plantName varchar(50) , " +
			" plantContext text(5000))";
	
	
	public static final String LAlarmInfo = "alarminfo";
	public static final String LAlarmInfo_CREATE = " create table alarminfo (" +
			" alarmId varchar(20) primary key , " +
			" alarmStatus boolean ," +
			" alarmTime varchar(20) , " +
			" alarmType integer(5) , " +
			" comment varchar(50) , " +
			" dataType integer(5) , " +
			" familyId varchar(20))";
	
	
	public static final String LAverageBloodPressureInfo = "LAverageBloodPressureInfo";
	public static final String LAverageBloodPressureInfo_CREATE = " create table LAverageBloodPressureInfo (" +
			" averageBPId varchar(20) primary key , " +
			" dataCount integer(5) ," +
			" dataDate varchar(20) , " +
			" dataNumber integer(5) , " +
			" dateType integer(5) , " +
			" day integer(5) , " +
			" deviceId varchar(20) , " +
			" deviceSn varchar(20) , " +
			" diastolicPressure double, " +
			" diastolicPressure_kpa double , " +
			" heartRate double , " +
			" hour integer(5) , " +
			" memberId varchar(20) , " +
			" minute integer(5) , " +
			" month integer(5) , " +
			" systolicPressure double, " +
			" systolicPressure_kpa double, " +
			" timePeriod integer(5), " +
			" totalDiaPressure double, " +
			" totalDiaPressure_kpa double, " +
			" totalHeartRate double, " +
			" totalSysPressure double, " +
			" totalSysPressure_kpa double, " +
			" userNo integer(5), " +
			" week integer(5), " +
			" year integer(5))";
	
	public static final String LAverageWeightInfo = "LAverageWeightInfo";
	public static final String LAverageWeightInfo_CREATE = " create table LAverageWeightInfo (" +
			" averageWeightId varchar(20) primary key , " +
			" dataDate integer(5) ," +
			" dateType integer(5) , " +
			" day integer(5) , " +
			" deviceId varchar(20) , " +
			" deviceSn varchar(20) , " +
			" hour integer(5) , " +
			" memberId varchar(20) , " +
			" minute integer(5), " +
			" month integer(5) , " +
			" pbf double , " +
			" pbfCount integer(5) , " +
			" pbfNumber integer(5) , " +
			" totalPbf double, " +
			" totalWeight double , " +
			" totalWeight_pound double, " +
			" userNo integer(5), " +
			" week integer(5), " +
			" weight double, " +
			" weight_pound double, " +
			" weightCount integer(5), " +
			" weightNumber integer(5), " +
			" year integer(5))";
	
	
	
	public static final String LBloodPressureInfo = "LBloodPressureInfo";
	public static final String LBloodPressureInfo_CREATE = " create table LBloodPressureInfo (" +
			" bloodPressureId varchar(50) primary key , " +
			" clientId varchar(20) ," +
			" comment varchar(50) , " +
			" createDate varchar(20) , " +
			" deleted integer(5) , " +
			" deviceId varchar(20) , " +
			" deviceSn varchar(20) , " +
			" diastolicPressure double , " +
			" diastolicPressure_kpa double , " +
			" familyId varchar(20), " +
			" heartRate double , " +
			" irregularHeartBeat integer(5) , " +
			" memberId varchar(20) , " +
			" movementError integer(5) , " +
			" pendingSync integer(5), " +
			" systolicPressure double , " +
			" systolicPressure_kpa double, " +
			" timePeriod integer(5), " +
			" unit varchar(20), " +
			" updateTime varchar(20), " +
			" userNo integer(5))";
	
	
	public static final String LDeviceInfo = "LDeviceInfo";
	public static final String LDeviceInfo_CREATE = " create table LDeviceInfo (" +
			" clientId integer primary key autoincrement, " +
			" connectionMethod integer(5) , " +
			" dateOfManufacture varchar(20) , " +
			" deleted integer(5) , " +
			" deviceId varchar(20) , " +
			" familyId varchar(20), " +
			" firmwareVersion varchar(20) , " +
			" hardwareVersion varchar(20) , " +
			" hourMode integer(5) , " +
			" identifier varchar(20) , " +
			" manufactureName varchar(20) , " +
			" maxUserQuantity integer(5) , " +
			" modelNum varchar(20) , " +
			" name varchar(20) , " +
			" password integer(5), " +
			" pendingActivate integer(5), " +
			" pendingSync integer(5), " +
			" productTypeCode varchar(20) , " +
			" sn varchar(20) , " +
			" softwareVersion varchar(20) , " +
			" timezone varchar(20) , " +
			" type integer(5), " +
			" unit varchar(20), " +
			" upateTime varchar(20), " +
			" usetimesOfLast30Days integer(5))";
	
	
	public static final String LDeviceUserInfo = "LDeviceUserInfo";
	public static final String LDeviceUserInfo_CREATE = " create table LDeviceUserInfo (" +
			" clientId integer primary key autoincrement, " +
			" deleted integer(5) , " +
			" deviceId varchar(20) , " +
			" familyId varchar(20), " +
			" deviceUserId varchar(20), " +
			" memberId varchar(20) , " +
			" pendingSync integer(5) , " +
			" productTypeCode varchar(20) , " +
			" upateTime varchar(20) , " +
			" usetimesOfLast30Days integer(5))";
	
	
	public static final String LFamilyInfo = "LFamilyInfo";
	public static final String LFamilyInfo_CREATE = " create table LFamilyInfo (" +
			" bpUnit varchar(20) primary key , " +
			" familyId varchar(20), " +
			" heightUnit varchar(20), " +
			" password varchar(20) , " +
			" username varchar(20) , " +
			" weightUnit varchar(20))";
	
	public static final String LMainEditDataInfo = "LMainEditDataInfo";
	public static final String LMainEditDataInfo_CREATE = " create table LMainEditDataInfo (" +
			" editData varchar(20) primary key , " +
			" memberId varchar(20))";
	
	
	public static final String LMemberInfo = "LMemberInfo";
	public static final String LMemberInfo_CREATE = " create table LMemberInfo (" +
			" birthday varchar(20) primary key , " +
			" bpUnit varchar(20) , " +
			" clientId varchar(20) , " +
			" createDate varchar(20) , " +
	//		" birthday varchar(20) , " +
			" deleted integer(5) , " +
			" email varchar(20) , " +
			" enableHealthItem integer(5) , " +
			" expectedDateOfChildBirth varchar(20) , " +
			" facePicture varchar(20) , " +
			" familyId varchar(20) , " +
			" fatherHeight double , " +
			" height double , " +
			" height_ft double , " +
			" heightOfBirth double , " +
			" heightUnit varchar(20) , " +
			" hourMode integer(5) , " +
			" memberId varchar(20) , " +
			" memberNo varchar(20) , " +
			" motherHeight double , " +
			" name varchar(20) , " +
			" password varchar(20) , " +
			" pendingSync integer(5) , " +
			" pregnanayFlag integer(5) , " +
			" sex integer(5) , " +
			" sportsGoal integer(5) , " +
			" updateTime varchar(20) , " +
			" username varchar(20) , " +
			" waist double , " +
			" waist_inch double , " +
			" weight double , " +
			" weight_pound double , " +
			" weightGoal double , " +
			" weightGoal_pound double , " +
			" weightOfBirth double , " +
			" weightTargetDate varchar(20) , " +
			" weightUnit varchar(20))";
	
	
	
	public static final String LSportInfo = "LSportInfo";
	public static final String LSportInfo_CREATE = " create table LSportInfo (" +
			" calories double , " +
			" clientId varchar(20) , " +
			" createDate varchar(20) , " +
			" deleted integer(5) , " +
			" deviceId varchar(20) , " +
			" deviceSn varchar(20) , " +
			" distance double , " +
			" familyId varchar(20) , " +
			" memberId varchar(20) , " +
			" memo varchar(20) , " +
			" pendingSync integer(5) , " +
			" sportId varchar(20) , " +
			" steps integer(5) , " +
			" updateTime varchar(20) , " +
			" userNo integer(5))";
	
	
	public static final String LTotalSportsInfo = "LTotalSportsInfo";
	public static final String LTotalSportsInfo_CREATE = " create table LTotalSportsInfo (" +
			" calories double , " +
			" dataCount integer(5) , " +
			" dataDate varchar(20) , " +
			" dateType integer(5) , " +
			" deviceId varchar(20) , " +
			" deviceSn varchar(20) , " +
			" distance integer(5) , " +
			" memberId varchar(20) , " +
			" steps integer(5) , " +
			" stepsGoal integer(5) , " +
			" totalSportsId varchar(20) , " +
			" userNo integer(5))";
	
	
	public static final String LWeightInfo = "LWeightInfo";
	public static final String LWeightInfo_CREATE = " create table LWeightInfo (" +
			" bmi double , " +
			" dataDate varchar(20) , " +
			" comment varchar(20) , " +
			" createDate varchar(20) , " +
			" deleted integer(5) , " +
			" deviceId varchar(20) , " +
			" deviceSn varchar(20) , " +
			" familyId varchar(20) , " +
			" memberId varchar(20) , " +
			" pbf double , " +
			" pendingSync integer(5) , " +
			" resistance_1 double , " +
			" resistance_2 double , " +
			" unit varchar(20) , " +
			" updateTime varchar(20) , " +
			" weightId varchar(20) , " +
			" weight double , " +
			" weight_pound double , " +
			" userNo integer(5))";
	
	public static String[] getCreateStr() {

		String[] AllCreate=new String[]{
//				LAlarmInfo_CREATE,
//				LBloodPressureInfo_CREATE,
//				LDeviceInfo_CREATE,
//				LDeviceUserInfo_CREATE,
//				LFamilyInfo_CREATE,
//				LMainEditDataInfo_CREATE,
//				LMemberInfo_CREATE,//mark
//				LSportInfo_CREATE,
//				LTotalSportsInfo_CREATE,
//				LWeightInfo_CREATE
				PlantInfo_CREATE
		};
		return AllCreate;
	}
}
