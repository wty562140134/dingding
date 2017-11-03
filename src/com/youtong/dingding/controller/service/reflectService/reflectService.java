package com.youtong.dingding.controller.service.reflectService;

import java.lang.reflect.InvocationTargetException;
import com.youtong.dingding.controller.service.abstractReflectService;

public class reflectService extends abstractReflectService {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T invokenFunction(Class<?> clazz, String callFunName,
			Object... params) {
		System.out.println(clazz);
		T obj = null;
		try {
			this.method = clazz.getDeclaredMethod(callFunName,
					getParamsClass(params));
			obj = (T) this.method.invoke(clazz.newInstance(), params);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T invokenFunction(String clazzName, String callFunName,
			Object... params) {
		T obj = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(clazzName);
			this.method = clazz.getDeclaredMethod(callFunName,
					getParamsClass(params));
			obj = (T) this.method.invoke(clazz.newInstance(), params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
