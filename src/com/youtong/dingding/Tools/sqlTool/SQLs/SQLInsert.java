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
			List<String> filesName, List<String> values,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL)
			throws RuntimeException {
		if (filesName.size() != values.size()) {
			throw new RuntimeException(
					"SQL insert filesName size NEQ values size");
		}
		setTableName(placeholder, tableName, getFullSQL);
		setFilesName(placeholder, filesName, whereValues, setValues, getFullSQL);
		setValues(placeholder, values, whereValues, setValues, getFullSQL);
		return this.TABLENAME + " " + this.FILENAME + " " + this.VALUES;
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
					"SQL insert into table name condition cannot be empty");
		}
		if (getFullSQL && placeholder.equals("%s")) {
			this.TABLENAME = "insert into " + tableName.get(0);
		} else {
			this.TABLENAME = "insert into " + placeholder;
		}
	}

	/**
	 * 设置字段名
	 * 
	 * @param placeholder
	 * @param filesName
	 * @param getFullSQL
	 */
	private void setFilesName(String placeholder, List<String> filesName,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		String sqlType = "insert";
		if (filesName == null || filesName.size() == 0) {
			this.FILENAME = "";
			return;
		}
		this.FILENAME = verdict(placeholder, sqlType, filesName, whereValues,
				setValues, getFullSQL);
	}

	private void setValues(String placeholder, List<String> values,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		String sqlType = "values";
		this.VALUES = sqlType
				+ " "
				+ verdict(placeholder, sqlType, values, whereValues, setValues,
						getFullSQL);
	}

}