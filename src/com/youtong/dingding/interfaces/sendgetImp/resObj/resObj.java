package com.youtong.dingding.interfaces.sendgetImp.resObj;

import java.util.Map;

import org.json.JSONObject;

/**
 * 返回结果json字符串的封装类
 * 
 * @author 123
 * 
 */
public class resObj {

	private JSONObject json = null;

	/**
	 * 无参数构造器,若创建类时不进行初始化可以之后调用setJsonStr()和setJsonMap()进行初始化
	 */
	public resObj() {
	}

	/**
	 * @param json
	 *            传入一个JSON对象初始化
	 */
	public resObj(JSONObject json) {
		this.json = json;
	}

	/**
	 * 初始化方法
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @throws Exception
	 *             传入参数非json字符串或此对象没有初始化时抛出错误
	 */
	public void setJsonStr(String jsonStr) throws Exception {
		if (this.json != null) {
			throw new Exception("this json object already init");
		} else if (jsonStr == null || jsonStr.equals("")) {
			throw new Exception("parameter jsonStr is null or nothing");
		}
		try {
			this.json = new JSONObject(jsonStr);
		} catch (Exception e) {
			throw new Exception("parameter str is not jsonStr");
		}
	}

	/**
	 * 重新设置对象的值
	 * 
	 * @param jsonStr
	 * @throws Exception
	 */
	public void resetJsonStr(String jsonStr) throws Exception {
		this.json = null;
		setJsonStr(jsonStr);
	}

	/**
	 * 初始化对象
	 * 
	 * @param map
	 *            Map<String,String>
	 * @throws Exception
	 *             传入参数非Map或此对象没有初始化时抛出错误
	 */
	public void setJsonMap(Map<String, String> map) throws Exception {
		if (this.json != null) {
			throw new Exception("this json object already init");
		}
		try {
			this.json = new JSONObject(map);
		} catch (Exception e) {
			throw new Exception("parameter str is not map");
		}
	}

	/**
	 * 重新设置对象的值
	 * 
	 * @param map
	 *            Map<String,String>
	 * @throws Exception
	 *             传入参数非Map或此对象没有初始化时抛出错误
	 */
	public void resetJsonMap(Map<String, String> map) throws Exception {
		this.json = null;
		setJsonMap(map);
	}

	/**
	 * 获取请求返回的json对象
	 * 
	 * @return json对象
	 */
	public JSONObject getJsonObj() {
		return this.json;
	}

	/**
	 * 获取请求返回json对象中的具体值
	 * 
	 * @param dataName
	 *            json对象中的Key
	 * @return json对象中Key的value
	 */
	@SuppressWarnings("unchecked")
	public <T> T getJsonData(String dataName) {
		return (T) this.json.get(dataName);
	}

}