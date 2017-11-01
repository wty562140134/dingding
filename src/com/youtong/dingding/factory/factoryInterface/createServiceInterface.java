package com.youtong.dingding.factory.factoryInterface;

import java.util.List;
import java.util.Map;

import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.controller.service.services.service;

public interface createServiceInterface {

	public <T extends service> T createService(Class<?> clazz,
			loadConfigFile load,
			Map<String, Map<String, List<String>>> paramMaps);

}
