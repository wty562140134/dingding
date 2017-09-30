package com.youtong.dingding.interfaces.sendgetImp.resObj;

public class test {
	public static void main(String[] args) {
		String jsonStr = "{'a':'apple','b':2}";
		resObj resObj = new resObj();
		try {
			resObj.setJsonStr(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jsonStr1 = "{'a':123,'b':223}";
		try {
			resObj.resetJsonStr(jsonStr1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(resObj.getJsonData("a"));
	}
}
