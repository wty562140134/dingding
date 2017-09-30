package com.youtong.dingding.Config

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.EhCachePlugin
import com.jfinal.template.Engine;
import com.youtong.dingding.controller.dingdingRoutes


class dingdingConfig extends JFinalConfig{

	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	public void configRoute(Routes me) {
		me.add(new dingdingRoutes());
	}

	public void configEngine(Engine me) {
	}

	public void configPlugin(Plugins me) {
		PropKit.use("commonfig.txt");
		me.add(new EhCachePlugin());
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}
}
