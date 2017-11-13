package com.youtong.dingding.controller.service.services.moduleServices;

import com.youtong.dingding.controller.service.services.abstractModuleServices;

public class testModuleService extends abstractModuleServices {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T start(T... param) {
		T a = null;
		a = (T) (demoString((String) param[0]) + ":" + demoInt((Integer) param[1]));
		return (T) a;
	}

	private String demoString(String a) {
		a += a;
		return a;
	}

	private Integer demoInt(Integer a) {
		a += 2;
		return a;
	}

	public static void main(String[] args) {
		abstractModuleServices test = new testModuleService();
		String a = "a";
		String b = (String) test.start(a, 3);
		System.out.println(b);
	}

}