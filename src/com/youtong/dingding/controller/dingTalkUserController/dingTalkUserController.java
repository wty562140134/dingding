package com.youtong.dingding.controller.dingTalkUserController;

import java.util.Map;

import com.youtong.dingding.controller.baseController;
import com.youtong.dingding.controller.service.abstractReflectService;
import com.youtong.dingding.controller.service.reflectService.reflectService;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.service.services.moduleServices.talkUserModuleService;
import com.youtong.dingding.controller.service.services.talkUser.talkUserService;

public class dingTalkUserController extends baseController {

	protected final abstractReflectService reflectService = initReflectService();
	protected final service talkUser = initService("getCodeList",
			talkUserService.class);

	public dingTalkUserController() {

	}

	public void index() {
		String code = getPara();
		if (code == null) {
			// @SuppressWarnings("unchecked")
			// Map<String, Object> resDat = (Map<String, Object>) reflectService
			// .invokenFunction(talkUserModuleService.class, "requestSend");
			Map<String, Object> resData = this.talkUser.requestSend();
			// setAttribute(resData);
		} else {
		}
	}

	public static void main(String[] args) {
		dingTalkUserController talk = new dingTalkUserController();
		talk.index();
	}

}