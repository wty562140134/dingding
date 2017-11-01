package com.youtong.dingding.controller.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.interfaces.sendgetImp.SendImp;

/**
 * service类的父类,是个抽象类
 * 
 * @author 123
 * 
 */
public abstract class baseService {

	protected loadConfigFile load;
	protected Map<String, Map<String, List<String>>> paramMaps;
	protected SendImp send = null;

	/**
	 * 构造函数,如向钉钉获取accessToken向https://oapi.dingtalk.com/gettoken
	 * 这个接口发送请求获取到的json为 {"errcode":0,"errmsg":ok,"access_token":"sldkfjiij1"}
	 * 
	 * @param load
	 *            配置文件读取的对象
	 * @param paramMaps
	 *            需要发送请求的Map<String, Map<String, List<String>>>
	 */
	public baseService(loadConfigFile load,
			Map<String, Map<String, List<String>>> paramMaps) {
		this.load = load;
		this.paramMaps = paramMaps;
		this.send = new SendImp(load.getURL());
	}

	/**
	 * 返回参数配置文件yaml迭代对象
	 * 
	 * @param <T>
	 * 
	 * @return
	 */
	protected Iterator<String> getIterator() {
		return this.paramMaps.keySet().iterator();
	}

	/**
	 * 向需要发送的接口发送请求
	 * 
	 * @param <T>
	 * 
	 * @return
	 */
	public abstract <T> T requestSend();

	/**
	 * 对发送请的参数进行处理,处理为Map
	 * 
	 * @param resDataName
	 *            建议使用请求成功后返回对象的需要自动名,如果需要的是整个对象用all代替
	 * 
	 * @return Map<String,String>
	 */
	protected abstract Map<String, String> getReqMap(String resDataName);

}