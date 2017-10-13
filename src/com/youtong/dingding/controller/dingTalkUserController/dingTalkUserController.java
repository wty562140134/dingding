package com.youtong.dingding.controller.dingTalkUserController;

import com.youtong.dingding.controller.baseController;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.service.services.talkUser.talkUserService;

public class dingTalkUserController extends baseController {

	public void index() {
		String code = getPara();
		if (code == null) {
			service talkUser = initService("getCodeList", talkUserService.class);
			talkUser.requestSend();
		} else {
		}
	}

	public static void main(String[] args) {
		dingTalkUserController talk = new dingTalkUserController();
		talk.index();
	}

}
