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
			List<String> filesName, List<String> wheres,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		setFilesName(placeholder, filesName, whereValues, setValues, getFullSQL);
		setTableName(placeholder, tableName, whereValues, setValues, getFullSQL);
		where(placeholder, wheres, whereValues, setValues, getFullSQL);
		return this.TABLENAME + " " + this.FROM + " " + this.WHERE;
	}

	/**
	 * 设置字段名
	 * 
	 * @param placeholder
	 * @param filesName
	 * @param getFullSQL
	 * 
	 * @throws RuntimeException
	 */
	private void setFilesName(String placeholder, List<String> filesName,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		String select = "select";
		try {
			this.TABLENAME = verdict(placeholder, select, filesName,
					whereValues, setValues, getFullSQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置表名
	 * 
	 * @param placeholder
	 * @param tableName
	 * @param getFullSQL
	 * 
	 * @throws RuntimeException
	 */
	private void setTableName(String placeholder, List<String> tableName,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		String from = "from";
		try {
			this.FROM = verdict(placeholder, from, tableName, whereValues,
					setValues, getFullSQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}