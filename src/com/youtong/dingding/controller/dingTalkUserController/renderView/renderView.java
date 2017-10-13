package com.youtong.dingding.controller.dingTalkUserController.renderView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.ehcache.CacheKit;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.interfaces.sendgetImp.SendImp;

/**
 * 向需要发送的接口发送请求,初始化时需传入读取配置文件的对象,需要发送请求的接口,其中发送请求的接口类型为MAP<key,value>
 * 这么封装是为了钉钉功能大多都是发送请求获取一个值,在用这个值进行下一步操作,传入MAP后自动遍历并发送请求,最后获取到需要的值后直接进行视图渲染
 * 
 * @author 123
 * 
 */
public class renderView {

	private loadConfigFile load;
	private Map<String, List<String>> interfaces;
	private String access_token;
	private String ticket;

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
	public renderView(loadConfigFile load, Map<String, List<String>> interfaces) {
		this.load = load;
		this.interfaces = interfaces;
	}

	/**
	 * 向需要发送的接口发送请求
	 * 
	 * @param <T>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T requestSend() {
		T response = null;
		Iterator<String> it = this.interfaces.keySet().iterator();
		while (it.hasNext()) {
			String interfaceName = it.next();
			List<String> reqTypeAndResDataName = this.interfaces
					.get(interfaceName);
			// 从缓存获取access_token或者ticket
			if (interfaceName.equals("gettoken")
					|| interfaceName.equals("get_jsapi_ticket")) {
				response = (T) JSONObject.parseObject(getCache(interfaceName,
						reqTypeAndResDataName));
			} else {
				response = (T) JSONObject.parseObject(new SendImp(
						load.getURL(), interfaceName,
						getReqMap(reqTypeAndResDataName.get(1)))
						.send(reqTypeAndResDataName.get(0)).getJsonObj()
						.toString());
			}

		}
		return (T) response;
	}

	/**
	 * 从缓存获取access_token和ticket认证,如果缓存里有就跳过,没有就发送请求获取
	 * 
	 * @param interfaceName
	 *            发送请求的接口
	 * @param reqTypeAndResDataName
	 *            List<String> index0存入的是请求类型GET or
	 *            POST,index1存入的是请求成功后需要的数据如access_token
	 * @return 缓存或发送请求的需要的返回值
	 */
	public String getCache(String interfaceName,
			List<String> reqTypeAndResDataName) {
		// 获取发送请求的Map
		Map<String, String> reqMap = getReqMap(reqTypeAndResDataName.get(1));
		// 这里以reqTypeAndResDataName.get(1)作为缓存Key使用
		Map<String, String> cacheMap = CacheKit.get("ACCESSTOKEN",
				reqTypeAndResDataName.get(1));
		if (cacheMap == null) {
			cacheMap = new HashMap<String, String>();
			cacheMap.put(
					reqTypeAndResDataName.get(1),
					new SendImp(load.getURL(), interfaceName, reqMap)
							.send(reqTypeAndResDataName.get(0)).getJsonObj()
							.toString());
			CacheKit.put("ACCESSTOKEN", reqTypeAndResDataName.get(1),
					cacheMap.get(reqTypeAndResDataName.get(1)));
		}
		return cacheMap.get(reqTypeAndResDataName.get(1));
	}

	/**
	 * 对发送请的参数进行处理,处理为Map
	 * 
	 * @param resData
	 *            成功后需要的数据的key
	 * 
	 * @return Map<String,String>
	 */
	private Map<String, String> getReqMap(String resDataName) {
		Map<String, String> reqMap = null;
		if (resDataName.equals("access_token")) {
			reqMap = load.getCorps();
		} else if (resDataName.equals("ticket")) {
			reqMap = CacheKit.get("ACCESSTOKEN", "access_token");
		}
		return reqMap;
	}

}
