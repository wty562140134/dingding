package com.youtong.dingding.Tools.loadConfig;

import java.util.HashMap;
import java.util.Map;

import com.youtong.dingding.Tools.baseLoadConfigFile;

/**
 * 配置文件读取类
 * 
 * @author 123
 * 
 */
public class loadConfigFile extends baseLoadConfigFile {

	private Map<String, String> corps = new HashMap<String, String>();
	private String URL = null;
	private String AgentID = null;

	/**
	 * 构成器
	 * 
	 * @param configFileName
	 *            需要读取配置文件的名字
	 */
	public loadConfigFile(String configFileName) {
		super(configFileName);
		setURL();
		setAgentID();
	}

	/**
	 * 获取配置文件中的CorpId和CorpSercret
	 * 
	 * @return Map
	 */
	public Map<String, String> getCorps() {
		corps.put("corpid", this.prop.get("CorpID"));
		corps.put("corpsecret", this.prop.get("CorpSecret"));
		return this.corps;
	}

	/**
	 * 获取配置文件中的数据
	 * 
	 * @param configName
	 *            配置文件中数据的名字
	 * @return 配置文件中的数据
	 * @throws Exception
	 *             如果没有这个配置抛出not find
	 */
	public String getConfigData(String configName) throws Exception {
		if (this.prop.get(configName) == null) {
			throw new Exception(configName + "not find");
		}
		return this.prop.get(configName);
	}

	/**
	 * 获取配置文件中的URL
	 * 
	 * @return
	 */
	public String getURL() {
		return this.URL;
	}

	public String getAgentID() {
		return this.AgentID;
	}

	private void setURL() {
		this.URL = this.prop.get("URL");
	}

	private void setAgentID() {
		this.AgentID = this.prop.get("AgentID");
	}
}
