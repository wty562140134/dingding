package com.youtong.dingding.Tools.sqlTool.SQLs;

import java.util.List;

import com.youtong.dingding.Tools.sqlTool.abstractSQL.abstractSQL;

public class SQLDelete extends abstractSQL {

	private String TABLENAME;

	@Override
	public String getSQL(String placeholder, List<String> tableName,
			List<String> filesName, List<String> wheres,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		setTableName(placeholder, tableName, getFullSQL);
		where(placeholder, wheres, whereValues, setValues, getFullSQL);
		return this.TABLENAME + " " + this.WHERE;
	}

	/**
	 * 设置表名
	 * 
	 * @param placeholder
	 * @param tableName
	 * @param getFullSQL
	 */
	private void setTableName(String placeholder, List<String> tableName,
			Boolean getFullSQL) {
		if (tableName == null || tableName.size() == 0) {
			throw new RuntimeException(
					"SQL delete tableName condition cannot be empty");
		}
		if (getFullSQL && placeholder.equals("%s")) {
			this.TABLENAME = "delete from " + tableName.get(0);
		} else {
			this.TABLENAME = "delete from " + placeholder;
		}
	}
}