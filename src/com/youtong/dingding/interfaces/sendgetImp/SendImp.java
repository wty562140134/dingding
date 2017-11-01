package com.youtong.dingding.interfaces.sendgetImp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.jfinal.kit.HttpKit;
import com.youtong.dingding.interfaces.Send;
import com.youtong.dingding.interfaces.sendgetImp.resObj.resObj;

/**
 * 发送请求接口此接口同意处理发送给钉钉的请求
 * 
 * @author 123
 * 
 */
public class SendImp implements Send {

	private String url = null;
	private Map<String, String> corps = null;
	private resObj resObj = new resObj();

	/**
	 * 
	 * @param url
	 *            发送请求url 如:http://www.baidu.com
	 * @param sendInterface
	 *            发送请求的接口 如:getUser
	 * @param param
	 *            发送请求参数MAP<String,String>
	 * 
	 */
	public SendImp(String url, String sendInterface, Map<String, String> param) {
		this.url = url + "/" + sendInterface;
		this.corps = param;
	}

	/**
	 * 
	 * @param url
	 *            发送请求url 如:http://www.baidu.com/getUser
	 * @param corps
	 *            发送请求的参数MAP<String,String>
	 * @return
	 */
	public SendImp(String url, Map<String, String> corps) {
		this.url = url;
		this.corps = corps;
	}

	/**
	 * 此构造器为发送GET请求完整的URL,初始化完整的URL如:http://www.baidu.com/getUser?userName=jack
	 * 
	 * @param url
	 *            如:http://www.baidu.com
	 * @param sendInterface
	 *            如:getUser
	 * @param sendName
	 *            如:userName
	 * @param sendValue
	 *            如:jack
	 */
	public SendImp(String url, String sendInterface, String sendName,
			String sendValue) {
		String sendUrl = url + "/" + sendInterface + "?" + "%s=" + "%s";
		this.url = String.format(sendUrl, sendName, sendValue);
	}

	public SendImp(String url) {
		this.url = url;
	}

	/**
	 * 此方法需配合发送GET请求完整的URL的构造器使用
	 * 
	 * @return resObj
	 */
	public resObj sendGET() {
		try {
			resObj.setJsonStr(HttpKit.get(this.url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.resObj;
	}

	/**
	 * 清空发送请求对象
	 */
	public SendImp resetAll(String sendUrl, Map<String, String> params) {
		this.corps = params;
		this.url = sendUrl;
		return this;
	}

	public SendImp setSendInterface(String sendInterface) {
		this.url += "/" + sendInterface;
		return this;
	}

	public SendImp setParam(Map<String, String> param) {
		this.corps = param;
		return this;
	}

	/**
	 * 发送请求方法
	 * 
	 * @param reqType
	 *            请求的方式GET or POST
	 * @return reqObj 返回值为封装JSON数据的reqObj类
	 */
	public resObj send(String reqType) {
		if (reqType.equals("GET")) {
			try {
				resObj.setJsonStr(HttpKit.get(this.url, this.corps));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (reqType.equals("POST")) {
			try {
				resObj.setJsonStr(HttpKit.post(this.url, new JSONObject(
						this.corps).toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.resObj;
	}

	public static void main(String[] args) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("a", "asdfkjlkjiej");
		SendImp send = new SendImp("www.baidu.com", param);
		send.setSendInterface("getUser");
	}

}