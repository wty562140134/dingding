package com.youtong.dingding.Tools.sqlTool;

import com.youtong.dingding.Tools.sqlTool.SQLs.SQLDelete;
import com.youtong.dingding.Tools.sqlTool.SQLs.SQLInsert;
import com.youtong.dingding.Tools.sqlTool.SQLs.SQLSelect;
import com.youtong.dingding.Tools.sqlTool.SQLs.SQLUpdate;

public class testSQLStr {

	public static void main(String[] args) {
		SQLStringUtil SQL = new SQLStringUtil();
		String sql = null;
		sql = SQL.setSQLFileName("name", "age").setSQLTableName("user")
				.setSQLWhere("id").setSQLWhereValues("192.168.10.111")
				.getSQL(new SQLSelect(), true);
		System.out.println(sql);

		sql = SQL.setSQLTableName("computer").setSQLWhere("id", "ip")
				.setSQLWhereValues("1", "192.110.12.333")
				.getSQL(new SQLDelete(), true);
		System.out.println(sql);

		sql = SQL.setSQLTableName("com").setSQLFileName("id", "ip")
				.setSQLSetValues("1", "192.168.11.111").setSQLWhere("id", "gg")
				.setSQLWhereValues("3", "dd").getSQL(new SQLUpdate(), true);
		System.out.println(sql);

		sql = SQL.setSQLTableName("kkk").setSQLFileName("name", "address")
				.setSQLValue("jack", "kunming").getSQL(new SQLInsert(), true);
		System.out.println(sql);

	}
}