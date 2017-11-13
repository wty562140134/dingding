package com.youtong.dingding.controller.service.reflectService;

import java.lang.reflect.InvocationTargetException;

import com.youtong.dingding.controller.service.abstractReflectService;

public class reflectService extends abstractReflectService {

	private final String moduleService = "com.youtong.dingding.controller.service.services.moduleServices.";

	@SuppressWarnings({ "unchecked" })
	@Override
	public <T> T invokenFunction(Class<?> clazz, String callFunName,
			Object... params) {
		T obj = null;
		try {
			obj = (T) getMed(clazz, callFunName, getParamsClass(params))
					.invoke(getInstance(clazz), params);
		} catch (SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public <T> T invokenFunction(String clazzName, String callFunName,
			Object... params) {
		T obj = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(this.moduleService + clazzName);
			obj = (T) getMed(clazz, callFunName, getParamsClass(params))
					.invoke(getInstance(clazzName), params);
		} catch (SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance(Class<?> clazz) {
		T Instance = null;
		try {
			Instance = (T) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return Instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance(String clazzName) {
		T Instance = null;
		try {
			Instance = (T) Class.forName(this.moduleService + clazzName)
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Instance;
	}
}