package com.youtong.dingding.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.factoryUtile.factoryUtile;
import com.youtong.dingding.controller.service.services.service;
import com.youtong.dingding.controller.service.services.talkUser.talkUserService;
import com.youtong.dingding.controller.serviceFactory.abstractServiceFactory;

public class baseController extends Controller {

	private loadConfigFile load = null;
	private loadYaml yaml = null;
	protected abstractServiceFactory service = factoryUtile.getFactory();

	public baseController() {
		this.load = new loadConfigFile("accessToken.cfg");
		this.yaml = new loadYaml("paramConfig.yaml");
	}

	public void index() {
		String code = getPara();
		if (code == null) {
			Map<String, Map<String, List<String>>> paramMap = factoryUtile
					.getParamMap(this.yaml, "getCodeList");
			service talkUser = service.createService(talkUserService.class,
					this.load, paramMap);
			talkUser.requestSend();
		} else {

		}
	}

	public static void main(String[] args) {
		baseController con = new baseController();
		con.index();
	}
}
