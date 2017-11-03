package com.youtong.dingding.Tools.sqlTool.SQLs;

import java.util.List;

import com.youtong.dingding.Tools.sqlTool.abstractSQL.abstractSQL;

public class SQLInsert extends abstractSQL {

	private String TABLENAME;
	private String FILENAME;
	private String VALUES;

	public SQLInsert() {
	}

	@Override
	public String getSQL(String placeholder, List<String> tableName,
			List<String> filesName, List<String> values)
			throws RuntimeException {
		setTableName(tableName.get(0));
		setFilesName(placeholder, filesName);
		setValues(placeholder, values);
		return this.TABLENAME + " " + this.FILENAME + " " + this.VALUES;
	}

	/**
	 * 设置表名
	 * 
	 * @param tableName
	 */
	private void setTableName(String tableName) {
		if (tableName == null || tableName.trim().length() == 0) {
			throw new RuntimeException(
					"insert into table name condition cannot be empty");
		}
		this.TABLENAME = "insert into" + " " + tableName;
	}

	/**
	 * 设置字段名
	 * 
	 * @param placeholder
	 * @param filesName
	 */
	private void setFilesName(String placeholder, List<String> filesName) {
		String sqlType = "insert";
		this.FILENAME = verdict(placeholder, sqlType, filesName);
	}

	private void setValues(String placeholder, List<String> values) {
		String sqlType = "values";
		this.VALUES = sqlType + " " + verdict(placeholder, sqlType, values);
	}

}