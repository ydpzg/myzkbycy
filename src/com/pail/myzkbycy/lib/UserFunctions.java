package com.pail.myzkbycy.lib;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class UserFunctions {
	
	private JSONParser jsonParser;
	private static String IPaddr = "http://www.zkbycy.com/phone/index.php";
//	private static String IPaddr = "http://ydpproject001.sinaapp.com/";
	private static String loginURL = IPaddr;
	private static String registerURL = IPaddr;
	private static String storeURL = IPaddr;
	private static String getPositionURL = IPaddr;
	private static String getAllUserURL = IPaddr;
	private static String getAllFriendsURL = IPaddr;
	private static String addOrDelFriendURL = IPaddr;
	private static String invotefriendsURL = IPaddr;
	private static String sendSelectedFriendsURL = IPaddr;
	private static String sendRejectFriendsURL = IPaddr;
	
	private static String tag_notification = "notification";
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String store_tag = "store";
	private static String getPosition_tag = "getPosition";
	private static String getAllPosition_tag = "getAllPosition";
	private static String getAllUser_tag = "getAllUser";
	private static String getAllFriends_tag = "getAllFriends";
	private static String addOrDelFriend_tag = "addOrDelFriend";
	private static String invotefriends_tag = "invotefriends";
	private static String sendSelectedFriends_tag = "sendSelectedFriends";
	private static String sendRejectFriends_tag = "sendRejectFriends";
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	public static ProgressDialog  createProgressDialog(Context mContext, String str) {		
		ProgressDialog pDialog = new ProgressDialog(mContext);
		pDialog.setMessage(str);
		pDialog.setIndeterminate(false);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setCancelable(true);
		pDialog.show();
		return pDialog;
	}
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject getNotification(String testString){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "notification"));
		params.add(new BasicNameValuePair("testString", testString));
		JSONObject json = jsonParser.getJSONFromUrl(IPaddr, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getPlantDetail(String plantId, String interTag){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "plant_detail"));
		params.add(new BasicNameValuePair("inter_tag", interTag));
		params.add(new BasicNameValuePair("plant_id", plantId));
		JSONObject json = jsonParser.getJSONFromUrl(IPaddr, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getAllPlant(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "all_plant"));
		JSONObject json = jsonParser.getJSONFromUrl(IPaddr, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	//"getUserActive"
	public JSONObject getUnsubscribe(String login_name, String inter_tag, String userActiveValue){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "unsubscribe"));
		params.add(new BasicNameValuePair("login_name", login_name));
		params.add(new BasicNameValuePair("inter_tag", inter_tag));
		if(!userActiveValue.equals("0") ) {
			params.add(new BasicNameValuePair("userActiveValue", userActiveValue));
		}
		JSONObject json = jsonParser.getJSONFromUrl(IPaddr, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getWeekOffer(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "week_offer"));
		JSONObject json = jsonParser.getJSONFromUrl(IPaddr, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getUserInf(String login_name){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "user_inf"));
		params.add(new BasicNameValuePair("login_name", login_name));
		JSONObject json = jsonParser.getJSONFromUrl(IPaddr, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject loginUser(String login_name, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("login_name", login_name));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject storePosition(String email, String longitude, String latitude, String addr){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", store_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("longitude", longitude));
		params.add(new BasicNameValuePair("latitude", latitude));
		JSONObject json = jsonParser.getJSONFromUrl(storeURL + "index.php?addr=" + addr, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getPosition(String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getPosition_tag));
		params.add(new BasicNameValuePair("email", email));
		JSONObject json = jsonParser.getJSONFromUrl(getPositionURL, params);
		return json;
	}
	public JSONObject sendSelectedFriends(String email, String selectedFriends) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", sendSelectedFriends_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("selectedFriends", selectedFriends));
		Log.i("test", selectedFriends);
		JSONObject json = jsonParser.getJSONFromUrl(sendSelectedFriendsURL, params);
		return json;
	}
	public JSONObject sendRejectFriends(String email, String selectedFriends) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", sendRejectFriends_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("selectedFriends", selectedFriends));
		Log.i("test", selectedFriends);
		JSONObject json = jsonParser.getJSONFromUrl(sendRejectFriendsURL, params);
		return json;
	}
	public JSONObject getAllUser(String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllUser_tag));
		params.add(new BasicNameValuePair("email", email));
		JSONObject json = jsonParser.getJSONFromUrl(getAllUserURL, params);
		return json;
	}
	public JSONObject getAllPosition(String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllPosition_tag));
		params.add(new BasicNameValuePair("email", email));
		JSONObject json = jsonParser.getJSONFromUrl(getPositionURL, params);
		return json;
	}
	public JSONObject getAllFriends(String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllFriends_tag));
		params.add(new BasicNameValuePair("email", email));
		JSONObject json = jsonParser.getJSONFromUrl(getAllFriendsURL, params);
		return json;
	}
	public JSONObject addFriend(String email, String friendName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", addOrDelFriend_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("friendName", friendName));
		params.add(new BasicNameValuePair("op", "add"));
		JSONObject json = jsonParser.getJSONFromUrl(addOrDelFriendURL, params);
		return json;
	}
	public JSONObject delFriend(String email, String friendName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", addOrDelFriend_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("friendName", friendName));
		params.add(new BasicNameValuePair("op", "del"));
		JSONObject json = jsonParser.getJSONFromUrl(addOrDelFriendURL, params);
		return json;
	}
	public JSONObject invotefriends(String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", invotefriends_tag));
		params.add(new BasicNameValuePair("email", email));
		JSONObject json = jsonParser.getJSONFromUrl(invotefriendsURL, params);
		return json;
	}
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function get login user name
	 * */
	public String getUserName(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		String loginName = db.getLoginUserName();
		return loginName;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
}
