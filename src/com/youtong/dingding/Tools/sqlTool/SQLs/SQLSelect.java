package com.youtong.dingding.Tools.sqlTool.SQLs;

import java.util.List;

import com.youtong.dingding.Tools.sqlTool.abstractSQL.abstractSQL;

public class SQLSelect extends abstractSQL {

	private String TABLENAME;
	private String FROM;

	public SQLSelect() {
	}

	@Override
	public String getSQL(String placeholder, List<String> tableName,
			List<String> filesName, List<String> wheres) {
		setTableName(placeholder, tableName);
		setFilesName(placeholder, filesName);
		where(placeholder, wheres);
		return this.TABLENAME + " " + this.FROM + " " + this.WHERE;
	}

	/**
	 * 设置表名
	 * 
	 * @param tableName
	 * @throws RuntimeException
	 */
	private void setTableName(String placeholder, List<String> tableName) {
		String from = "from";
		this.FROM = verdict(placeholder, from, tableName);
	}

	/**
	 * 设置字段名
	 * 
	 * @param setFilesName
	 * @throws RuntimeException
	 */
	private void setFilesName(String placeholder, List<String> filesName) {
		String select = "select";
		this.TABLENAME = verdict(placeholder, select, filesName);
	}

}