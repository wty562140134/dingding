package com.youtong.dingding.controller.serviceFactory.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;
import com.youtong.dingding.controller.service.abstractReflectService;
import com.youtong.dingding.controller.service.services.service;

/**
 * 工厂类的实体类
 * 
 * @author 123
 * 
 */
public class serviceFactory extends abstractServiceFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends service> T productionService(Class<?> clazz,
			loadConfigFile load,
			Map<String, Map<String, List<String>>> paramMaps) {
		service service = null;
		try {
			// 调用创建类的构造函数
			Constructor con = clazz.getConstructor(loadConfigFile.class,
					Map.class);
			// 通过获取到的构造函数实例化需要创建的service
			service = (service) con.newInstance(load, paramMaps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) service;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends abstractReflectService> T productionReflect(Class<?> clazz) {
		T ref = null;
		try {
			ref = (T) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return (T) ref;
	}
}