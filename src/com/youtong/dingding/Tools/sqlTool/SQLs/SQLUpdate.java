package com.youtong.dingding.Tools.sqlTool.SQLs;

import java.util.List;

import com.youtong.dingding.Tools.sqlTool.abstractSQL.abstractSQL;

public class SQLUpdate extends abstractSQL {

	private String TABLENAME;
	private String SETNAME;

	public SQLUpdate() {
	}

	@Override
	public String getSQL(String placeholder, List<String> tableName,
			List<String> filesName, List<String> wheres) {
		setTableName(tableName.get(0));
		setFilesName(placeholder, filesName);
		where(placeholder, wheres);
		return this.TABLENAME + " " + this.SETNAME + " " + this.WHERE;
	}

	/**
	 * 设置表名
	 * 
	 * @param tableName
	 */
	private void setTableName(String tableName) {
		if (tableName == null || tableName.trim().length() == 0) {
			throw new RuntimeException(
					"update table name condition cannot be empty");
		}
		this.TABLENAME = "update " + tableName;
	}

	/**
	 * 设置字段名
	 * 
	 * @param placeholder
	 * @param filesName
	 */
	private void setFilesName(String placeholder, List<String> filesName) {
		String sqlType = "set";
		this.SETNAME = verdict(placeholder, sqlType, filesName);
	}

}