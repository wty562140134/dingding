package com.youtong.dingding.controller


import com.jfinal.config.Routes
import com.youtong.dingding.controller.IsvReceiv.IsvReceivController
import com.youtong.dingding.controller.dingTalkUser.dingTalkUserController

class dingdingRoutes extends Routes {

	public void config() {
		setBaseViewPath("/WEB-INF/view")
		//add("/IsvReceiv",IsvReceivController.class)
		add("/dingding", dingTalkUserController.class)
	}
}
