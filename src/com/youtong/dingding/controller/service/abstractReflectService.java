package com.youtong.dingding.controller.service;

import java.lang.reflect.Method;

public abstract class abstractReflectService {

	/**
	 * 反射调用函数的函数，使用无参构造函数
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
	 * 反射调用函数的函数重载函数，使用无参构造函数
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
	 * 反射实例化类函数，使用无参构造函数
	 * 
	 * @param clazz
	 *            需要实例化类所在类
	 * @return
	 */
	public abstract <T> T getInstance(Class<?> clazz);

	/**
	 * 反射实例化类重载函数，使用无参构造函数
	 * 
	 * @param clazzName
	 *            需要实例化类的全名，路径名+类名
	 * @return
	 */
	public abstract <T> T getInstance(String clazzName);

	/**
	 * 获取调用函数
	 * 
	 * @param clazz
	 *            调用函数所在类
	 * @param funName
	 *            调用函数名
	 * @param args
	 *            调用函数传入参数
	 * @return 调用函数
	 */
	protected Method getMed(Class<?> clazz, String funName, Class<?>... args) {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(funName, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return method;
	}

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