package com.youtong.dingding.factory.factory.serviceFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.factory.Factory;
import com.youtong.dingding.factory.factoryInterface.createServiceInterface;

public class serviceFactory extends Factory implements createServiceInterface {

	@Override
	public <T extends service> T createService(Class<?> clazz,
			loadConfigFile load,
			Map<String, Map<String, List<String>>> paramMaps) {
		service service = null;
		try {
			// 传入需要创建的类
			Class cla = Class.forName(clazz.getName());
			// 调用创建类的构造函数
			Constructor con = cla.getConstructor(loadConfigFile.class,
					Map.class);
			// 通过获取到的构造函数实例化需要创建的service
			service = (service) con.newInstance(load, paramMaps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) service;
	}

	@Override
	public <T> T creator() {
		// TODO Auto-generated method stub
		return null;
	}

}
