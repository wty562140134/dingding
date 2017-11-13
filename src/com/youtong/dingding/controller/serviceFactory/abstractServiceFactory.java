package com.youtong.dingding.controller.serviceFactory;

import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.controller.service.abstractReflectService;
import com.youtong.dingding.controller.service.services.service;

/**
 * 创建service的抽象类
 * 
 * @author 123
 * 
 */
public abstract class abstractServiceFactory {

	/**
	 * 创建service的抽象方法
	 * 
	 * @param clazz
	 *            需要创建的service类的class文件
	 * @param load
	 *            需要读取的配置文件
	 * @param param
	 *            需要service发送的请求参数
	 * @return
	 */
	public abstract <T extends service> T productionService(Class<?> clazz,
			loadConfigFile load,
			Map<String, Map<String, List<String>>> paramMaps);

	/**
	 * 创建反射类的封装函数
	 * 
	 * @param clazz
	 *            继承reflectService的反射类
	 * 
	 * @return
	 */
	public abstract <T extends abstractReflectService> T productionReflect(
			Class<?> clazz);

}