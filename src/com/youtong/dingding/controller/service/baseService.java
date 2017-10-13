package com.youtong.dingding.controller.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.interfaces.sendgetImp.SendImp;

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
	 * @param interfaces
	 *            需要发送请求的MAP<String,List<String>>
	 *            key为发送请求接口,value.get(0)为发送请求类型GET or
	 *            POST,value.get(1)为获取响应json需要的值key例如: List<String> mapList =
	 *            new ArrayList<String>(); mapList.add("GET");
	 *            mapList.add("access_token");
	 *            interfaces.put("gettoken",mapList);
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
	 * @param resData
	 *            成功后需要的数据的key
	 * 
	 * @return Map<String,String>
	 */
	protected abstract Map<String, String> getReqMap(String resDataName);

}
