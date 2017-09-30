package com.youtong.dingding.Tools;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class baseLoadConfigFile {

	protected Prop prop = null;

	public baseLoadConfigFile(String configFileName) {
		this.prop = PropKit.use(configFileName);
	}

}
