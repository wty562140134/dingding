package com.youtong.dingding.controller.dingTalkUserController;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.dingTalkUserController.renderView.renderView;
import com.youtong.dingding.controller.factoryUtile.factoryUtile;
import com.youtong.dingding.controller.service.baseService;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.service.services.talkUser.talkUserService;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;

public class dingTalkUserController extends Controller {

	private loadConfigFile load = null;
	private loadYaml yaml = null;
	private abstractServiceFactory service = factoryUtile.getFactory();

	public dingTalkUserController() {
		this.load = new loadConfigFile("accessToken.cfg");
		this.yaml = new loadYaml("paramConfig.yaml");
	}

	public void index() {
		String code = getPara();
		if (code == null) {
			Map<String, Map<String, List<String>>> paramMaps = factoryUtile
					.getParamMap(this.yaml, "getCodeList");
			service talkUser = service.createService(talkUserService.class,
					this.load, paramMaps);
			talkUser.requestSend();

			// String nonceStr = "nonceStr"
			// long timeStamp = System.currentTimeMillis()/1000
			// def signature =AuthHelper.sign(ticket, nonceStr, timeStamp,
			// load.getURL())
			// setAttr("agentId", load.getAgentID())
			// setAttr("corpId", load.getCorps().get("CorpID"))
			// setAttr("timeStamp", timeStamp)
			// setAttr("nonceStr", nonceStr)
			// setAttr("signature", signature)
			// render("index.html")
		} else {
		}
	}

	public static void main(String[] args) {
		dingTalkUserController ding = new dingTalkUserController();
		ding.index();
	}
}
