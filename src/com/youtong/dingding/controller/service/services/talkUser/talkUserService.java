package com.youtong.dingding.controller.service.services.talkUser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.ehcache.CacheKit;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.controller.service.services.service;

public class talkUserService extends service {

	public talkUserService(loadConfigFile load,
			Map<String, Map<String, List<String>>> paramMaps) {
		super(load, paramMaps);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T requestSend() {
		T response = null;
		while (getIterator().hasNext()) {
			String paramMapName = getIterator().next();
			Map<String, List<String>> paramMap = this.paramMaps
					.get(paramMapName);
			System.out.println(paramMap);
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String sendInterface = it.next();
				List<String> sendTypeAndResDataName = paramMap
						.get(sendInterface);
				// 从缓存获取access_token或者ticket
				response = (T) JSONObject.parseObject(getCache(sendInterface,
						sendTypeAndResDataName));
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
	private String getCache(String sendInterface,
			List<String> sendTypeAndResDataName) {
		// 获取发送请求的Map
		Map<String, String> reqMap = getReqMap(sendTypeAndResDataName.get(1));
		// 这里以reqTypeAndResDataName.get(1)作为缓存Key使用
		Map<String, String> cacheMap = CacheKit.get("ACCESSTOKEN",
				sendTypeAndResDataName.get(1));
		if (cacheMap == null) {
			cacheMap = new HashMap<String, String>();
			cacheMap.put(sendTypeAndResDataName.get(1),
					this.send.setSendInterface(sendInterface).setParam(reqMap)
							.send(sendTypeAndResDataName.get(0)).getJsonObj()
							.toString());
			CacheKit.put("ACCESSTOKEN", sendTypeAndResDataName.get(1),
					cacheMap.get(sendTypeAndResDataName.get(1)));
		}
		return cacheMap.get(sendTypeAndResDataName.get(1));
	}

}