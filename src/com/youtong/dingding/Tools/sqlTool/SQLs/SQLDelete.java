package com.youtong.dingding.Tools.sqlTool.SQLs;

import java.util.List;

import com.youtong.dingding.Tools.sqlTool.abstractSQL.abstractSQL;

public class SQLDelete extends abstractSQL {

	private String TABLENAME;

	@Override
	public String getSQL(String placeholder, List<String> tableName,
			List<String> filesName, List<String> wheres) {
		setTableName(tableName.get(0));
		where(placeholder, wheres);
		return this.TABLENAME + " " + this.WHERE;
	}

	private void setTableName(String tableName) {
		if (tableName == null || tableName.trim().length() == 0) {
			throw new RuntimeException(
					"delete tableName condition cannot be empty");
		}
		this.TABLENAME = "delete from" + " " + tableName;
	}

}