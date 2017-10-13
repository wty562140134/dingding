package com.youtong.dingding.controller.serviceFactory.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;
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
	public <T> T productionService(Class<T> clazz, loadConfigFile load,
			Map<String, Map<String, List<String>>> param) {
		service service = null;
		try {
			// 传入需要创建的类
			Class cla = Class.forName(clazz.getName());
			// 调用创建类的构造函数
			Constructor con = cla.getConstructor(loadConfigFile.class,
					Map.class);
			// 通过获取到的构造函数实例化需要创建的service
			service = (service) con.newInstance(load, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) service;
	}
}
