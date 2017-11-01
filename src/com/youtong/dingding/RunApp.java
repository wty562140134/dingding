package com.youtong.dingding;

import com.jfinal.core.JFinal;

public class RunApp {

	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/");
	}

}