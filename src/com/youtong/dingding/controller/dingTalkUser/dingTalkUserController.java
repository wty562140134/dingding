package com.youtong.dingding.controller.dingTalkUser;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.youtong.dingding.Tools.loadConfig.loadConfigFile;
import com.youtong.dingding.Tools.yamlLoad.loadYaml;
import com.youtong.dingding.controller.dingTalkUser.renderView.renderView;

public class dingTalkUserController extends Controller {

	private loadConfigFile load = null;
	private loadYaml yaml = null;

	public dingTalkUserController() {
		this.load = new loadConfigFile("accessToken.cfg");
		this.yaml = new loadYaml("paramConfig.yaml");
	}

	public void index() {
		String code = getPara();
		if (code == null) {
			// 放入需要发送请求的Map<String(请求的接口名),List<String(index0是请求类型,index1是请求成功返回json数据需要的名字)>>
			// 这么设计是为了扩展程序可使用性,最上层设置一个Map放入相应的接口以及需要获取的数据名就可以不用改动后台

			Map<String, Object> paramMap = yaml.getYamlData();
			@SuppressWarnings("unchecked")
			List<Object> paramList = (List<Object>) paramMap.get("getCodeList");
			for (int i = 0; i < paramList.size(); i++) {
				String para = "paramMap%s";
				@SuppressWarnings("unchecked")
				Map<String, List<String>> param = (Map<String, List<String>>) paramList
						.get(i);
				para = String.format(para, i + 1);
				@SuppressWarnings("unchecked")
				Map<String, List<String>> interfaces = (Map<String, List<String>>) param
						.get(para);
				renderView renderView = new renderView(load, interfaces);
				Map<String, String> response = renderView.requestSend();
			}

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
		// @SuppressWarnings("unchecked")
		// Map<String, Object> map = (Map<String, Object>) ding.index();
		// @SuppressWarnings("unchecked")
		// List<Object> list = (List<Object>) map.get("paramList");

		// for (int i = 0; i < list.size(); i++) {
		// String para = "paramMap%s";
		// @SuppressWarnings("unchecked")
		// Map<String, List<String>> param = (Map<String, List<String>>) list
		// .get(i);
		// para = String.format(para, i + 1);
		// System.out.println(param.get(para));
		// }
	}
}
