package com.youtong.dingding.controller.dingTalkUserController;

import java.util.Map;

import com.youtong.dingding.controller.baseController;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.service.services.talkUser.talkUserService;

public class dingTalkUserController extends baseController {

	private service talkUser = null;

	public dingTalkUserController() {
		this.talkUser = initService("getCodeList", talkUserService.class);

	}

	public void index() {
		String code = getPara();
		if (code == null) {
			Map<String, Object> resData = this.talkUser.requestSend();
			setAttribute(resData);
		} else {
		}
	}

	public static void main(String[] args) {
		dingTalkUserController talk = new dingTalkUserController();
		talk.index();
	}

}