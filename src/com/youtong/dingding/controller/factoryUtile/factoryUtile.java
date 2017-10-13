package com.youtong.dingding.controller.factoryUtile;

import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;
import com.youtong.dingding.controller.serviceFactory.factory.serviceFactory;

public class factoryUtile {

	/**
	 * 获取service工厂
	 * 
	 * @return
	 */
	public static abstractServiceFactory getFactory() {
		abstractServiceFactory service = new serviceFactory();// 实例化工厂
		return service;
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
		// Map<String, List<String>> sendInterface = null;
		// Map<String, Object> paramMap = yaml.getYamlData();
		// @SuppressWarnings("unchecked")
		// List<Object> paramList = (List<Object>) paramMap.get(yamlParam);
		// for (int i = 0; i < paramList.size(); i++) {
		// String para = "paramMap%s";
		// @SuppressWarnings("unchecked")
		// Map<String, List<String>> param = (Map<String, List<String>>)
		// paramList
		// .get(i);
		// para = String.format(para, i + 1);
		// @SuppressWarnings("unchecked")
		// Map<String, List<String>> interfaces = (Map<String, List<String>>)
		// param
		// .get(para);
		// // sendInterface = interfaces;
		// }
		return paramMap;
	}
}
