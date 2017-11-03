package com.youtong.dingding.controller.factoryUtile;

import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;
import com.youtong.dingding.controller.serviceFactory.factory.serviceFactory;

public class factoryUtile {

	/**
	 * 获取工厂
	 * 
	 * @return
	 */
	public static abstractServiceFactory getFactory() {
		abstractServiceFactory factory = new serviceFactory();// 实例化工厂
		return factory;
	}

	/**
	 * 获取yaml配置文件中的需要发送接口的参数
	 * 
	 * @param yaml
	 *            yaml配置文件
	 * @param yamlParam
	 *            yaml配置文件中具体获取的参数明如getCodeList或getUserInfo
	 * @return
	 */
	public static Map<String, Map<String, List<String>>> getParamMap(
			loadYaml yaml, String yamlParam) {
		Map<String, Object> yamlObj = yaml.getYamlData();
		@SuppressWarnings("unchecked")
		Map<String, Map<String, List<String>>> paramMap = (Map<String, Map<String, List<String>>>) yamlObj
				.get(yamlParam);
		return paramMap;
	}

}