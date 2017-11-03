package com.youtong.dingding.controller.service;

import java.lang.reflect.Method;

public abstract class abstractReflectService {

	protected Method method;

	/**
	 * 反射调用函数的函数
	 * 
	 * @param clazz
	 *            需要调用函数所在类
	 * @param callFunName
	 *            调用函数名
	 * @param params
	 *            调用函数传入参数
	 * @return
	 */
	public abstract <T> T invokenFunction(Class<?> clazz, String callFunName,
			Object... params);

	/**
	 * 反射调用函数的函数重载函数
	 * 
	 * @param clazzName
	 *            调用类全名，路径名+类名
	 * @param callFunName
	 *            调用函数名
	 * @param params
	 *            调用函数传入参数
	 * @return
	 */
	public abstract <T> T invokenFunction(String clazzName, String callFunName,
			Object... params);

	/**
	 * 获取反射类调用函数时候需要的参数所在的Class的方法
	 * 
	 * @param params
	 *            调用函数的参数
	 * @return
	 */
	protected Class<?>[] getParamsClass(Object... params) {
		Class<?>[] clazzs = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			clazzs[i] = params[i].getClass();
		}
		return clazzs;
	}

}