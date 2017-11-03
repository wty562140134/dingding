package com.youtong.dingding.controller;

import com.jfinal.config.Routes;
import com.youtong.dingding.controller.dingTalkUserController.dingTalkUserController;
import com.youtong.dingding.controller.testController.testController;

public class dingdingRoutes extends Routes {

	public void config() {
		setBaseViewPath("/WEB-INF/view");
		// add("/IsvReceiv",IsvReceivController.class)
		add("/test", testController.class);
		add("/dingding", dingTalkUserController.class);
	}
}