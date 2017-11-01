package com.youtong.dingding.controller;

import com.jfinal.config.Routes;
import com.youtong.dingding.controller.dingTalkUserController.dingTalkUserController;

public class dingdingRoutes extends Routes {

	public void config() {
		setBaseViewPath("/WEB-INF/view");
		// add("/IsvReceiv",IsvReceivController.class)
		add("/dingding", dingTalkUserController.class);
	}

}