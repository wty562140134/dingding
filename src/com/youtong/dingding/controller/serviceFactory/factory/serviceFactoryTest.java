package com.youtong.dingding.controller.serviceFactory.factory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.service.services.talkUser.talkUserService;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;

public class serviceFactoryTest {

	private loadConfigFile load = null;
	private loadYaml yaml = null;

	public serviceFactoryTest() {
		this.load = new loadConfigFile("accessToken.cfg");
		this.yaml = new loadYaml("paramConfig.yaml");
	}

	public static void main(String[] args) {

		serviceFactoryTest ser = new serviceFactoryTest();
		Map<String, Object> paramObj = ser.yaml.getYamlData();
		@SuppressWarnings("unchecked")
		Map<String, Map<String, List<String>>> paramMaps = (Map<String, Map<String, List<String>>>) paramObj
				.get("getCodeList");
		Iterator<String> keyIt = paramMaps.keySet().iterator();
		while (keyIt.hasNext()) {
			String paramMapName = keyIt.next();
			// System.out.println(paramMapName);
			Map<String, List<String>> paramMap = (Map<String, List<String>>) paramMaps
					.get(paramMapName);
			send(paramMap);
		}
	}

	public static <T> T send(Map<String, List<String>> paramMap) {
		// System.out.println(paramMap);
		T response = null;
		Iterator<String> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String sendInterface = it.next();
			List<String> sendTypeAndResDataName = paramMap.get(sendInterface);
			// System.out.println(sendTypeAndResDataName);
		}
		return null;
	}
}