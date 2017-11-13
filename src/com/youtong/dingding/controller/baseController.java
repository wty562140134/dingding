package com.youtong.dingding.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.factoryUtile.factoryUtile;
import com.youtong.dingding.controller.service.abstractReflectService;
import com.youtong.dingding.controller.service.reflectService.reflectService;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;

/**
 * 所有controller的父类.封装类创建service的方法
 * 
 * @author 123
 * 
 */
public class baseController extends Controller {

	protected final loadConfigFile load = new loadConfigFile("accessToken.cfg");
	protected final loadYaml yaml = new loadYaml("paramConfig.yaml");
	protected final abstractServiceFactory createService = factoryUtile
			.getFactory();

	public baseController() {
	}

	/**
	 * 创建继承service的封装方法
	 * 
	 * @param yamlParam
	 *            yaml配置文件中需要的参数名
	 * @param clazz
	 *            需要创建的service类
	 * @return 返回创建好的service
	 */
	@SuppressWarnings("unchecked")
	protected <T extends service> T initService(String yamlParamMapsName,
			Class<?> clazz) {
		Map<String, Map<String, List<String>>> paramMaps = factoryUtile
				.getParamMap(this.yaml, yamlParamMapsName);
		service creationService = (service) createService.productionService(
				clazz, this.load, paramMaps);
		return (T) creationService;
	}

	/**
	 * 创建反射类的封装函数
	 * 
	 * @param clazz
	 *            继承reflectService的反射类
	 * 
	 * @param useConstructor
	 *            true:使用无参构造函数，false:使用有参构造函数
	 * 
	 * @return
	 */
	protected <T extends abstractReflectService> T initReflectService(
			Class<?> clazz) {
		return createService.productionReflect(clazz);
	}

	protected <T extends abstractReflectService> T initReflectService() {
		return createService.productionReflect(reflectService.class);
	}

	protected void setAttribute(Object... obj) {
		for (int i = 0; i < obj.length; i++) {
			setAttr(obj[i].toString(), obj[i + 1]);
			i = i + 1;
		}
	}

}