package com.youtong.dingding.controller.service.services;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.ehcache.CacheKit;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.controller.service.baseService;

/**
 * baseService类的实现类
 * 
 * @author 123
 * 
 */
public class service extends baseService {

	public service(loadConfigFile load,
			Map<String, Map<String, List<String>>> paramMaps) {
		super(load, paramMaps);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T requestSend() {
		T response = null;
		// 迭代外层Map
		while (getIterator().hasNext()) {
			String paramMapName = getIterator().next();
			Map<String, List<String>> paramMap = this.paramMaps
					.get(paramMapName);
			// 迭代内层的Map才能获取到发送给dingding的具体参数
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				// 获取发送的接口
				String sendInterface = it.next();
				// 获取发送请求的类型和返回结果名称的List
				List<String> sendTypeAndResDataName = paramMap
						.get(sendInterface);
				Map<String, String> reqMap = getReqMap(sendTypeAndResDataName
						.get(1));
				response = (T) JSONObject.parseObject(this.send
						.setSendInterface(sendInterface).setParam(reqMap)
						.send(sendTypeAndResDataName.get(0)).getJsonObj()
						.toString());
			}
		}
		return (T) response;
	}

	@Override
	protected Map<String, String> getReqMap(String resDataName) {
		Map<String, String> reqMap = null;
		switch (resDataName) {
		case "access_token":
			reqMap = load.getCorps();
			break;

		case "ticket":
			reqMap = CacheKit.get("ACCESSTOKEN", "access_token");
			break;

		case "all":
			reqMap = null;
			break;

		default:
			break;
		}
		return reqMap;
	}

}