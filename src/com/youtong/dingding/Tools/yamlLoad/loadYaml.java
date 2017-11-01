package com.youtong.dingding.Tools.yamlLoad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * 读取配置文件yaml的工具类,new的时候把配置文件名传入
 * 
 * @author 123
 * 
 */
public class loadYaml {

	private String fileName = null;
	private Yaml yaml = new Yaml();

	public loadYaml(String fileName) {
		this.fileName = fileName;
	}

	private FileInputStream getConfigFile() {
		FileInputStream file = null;
		try {
			URL url = loadYaml.class.getClassLoader()
					.getResource(this.fileName);
			file = new FileInputStream(url.getFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获取配置文件中的内容
	 * 
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getYamlData() {
		return (Map<String, Object>) yaml.load(getConfigFile());
	}

	/**
	 * 测试使用方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "paramConfig.yaml";
		loadYaml ya = new loadYaml(fileName);
		Map<String, Object> map = ya.getYamlData();
		System.out.println(map);
	}

}