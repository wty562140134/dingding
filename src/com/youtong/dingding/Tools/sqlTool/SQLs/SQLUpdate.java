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
			List<String> filesName, List<String> wheres,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		setTableName(placeholder, tableName, getFullSQL);
		setFilesName(placeholder, filesName, whereValues, setValues, getFullSQL);
		where(placeholder, wheres, whereValues, setValues, getFullSQL);
		return this.TABLENAME + " " + this.SETNAME + " " + this.WHERE;
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
					"SQL update table name condition cannot be empty");
		}
		if (getFullSQL && placeholder.equals("%s")) {
			this.TABLENAME = "update " + tableName.get(0);
		} else {
			this.TABLENAME = "update " + placeholder;
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
		if (filesName == null || filesName.size() == 0) {
			throw new RuntimeException(
					"SQL update filesName condition cannot be empty");
		}
		String sqlType = "set";
		this.SETNAME = verdict(placeholder, sqlType, filesName, whereValues,
				setValues, getFullSQL);
	}
}