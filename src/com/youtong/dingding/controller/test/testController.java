package com.youtong.dingding.controller.test;

import com.youtong.dingding.controller.baseController;
import com.youtong.dingding.controller.service.reflectService.reflectService;
import com.youtong.dingding.controller.test.testService.testService;

public class testController extends baseController {

	public testController() {
	}

	public void index() {
		String s = initReflectService().invokenFunction(testService.class,
				"stringTest", "jack");
		renderText(s);
	}

	public void test() {
		/*
		 * 通过前端发送过来的参数去调用相应模块中的相应函数
		 * 如：http://127.0.0.1/test/test?clazzName=com.youtong
		 * .dingding.controller
		 * .test.testService.testService&callFunName=stringTest&params=tom
		 */
		String clazzName = getPara("clazzName");
		String callFunName = getPara("callFunName");
		String params = getPara("params");
		String s = initReflectService(reflectService.class).invokenFunction(
				clazzName, callFunName, params);
		renderText(s);
	}

}