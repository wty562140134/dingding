package com.youtong.dingding.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.factoryUtile.factoryUtile;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;

public class baseController extends Controller {

	private loadConfigFile load = null;
	private loadYaml yaml = null;
	protected abstractServiceFactory service = factoryUtile.getFactory();

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
	protected <T> T initService(String yamlParam, Class<T> clazz) {
		Map<String, Map<String, List<String>>> paramMap = factoryUtile
				.getParamMap(this.yaml, yamlParam);
		service creationService = (service) service.productionService(clazz,
				this.load, paramMap);
		return (T) creationService;
	}

}
