package com.youtong.dingding.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.factoryUtile.factoryUtile;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;

/**
 * 所有controller的父类.封装类创建service的方法
 * 
 * @author 123
 * 
 */
public class baseController extends Controller {

	protected loadConfigFile load = null;
	protected loadYaml yaml = null;
	protected abstractServiceFactory service = factoryUtile.getFactory();
	protected Map<String, Object> attrs;

	/**
	 * 构造函数,加载cfg和yaml配置文件
	 */
	public baseController() {
		this.load = new loadConfigFile("accessToken.cfg");
		this.yaml = new loadYaml("paramConfig.yaml");
	}

	/**
	 * 创建service的封装方法
	 * 
	 * @param yamlParam
	 *            yaml配置文件中需要的参数名
	 * @param clazz
	 *            需要创建的service类
	 * @return 返回创建好的service
	 */
	@SuppressWarnings("unchecked")
	protected <T> T initService(String yamlParamMapsName, Class<?> clazz) {
		Map<String, Map<String, List<String>>> paramMaps = factoryUtile
				.getParamMap(this.yaml, yamlParamMapsName);
		service creationService = (service) service.productionService(clazz,
				this.load, paramMaps);
		return (T) creationService;
	}

	protected void setAttribute(Map<String, Object> attrs) {
		this.attrs = attrs;
	}
}