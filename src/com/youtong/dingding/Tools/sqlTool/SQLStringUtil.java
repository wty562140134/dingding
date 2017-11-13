package com.youtong.dingding.Tools.sqlTool;

import java.util.Arrays;
import java.util.List;

import com.youtong.dingding.Tools.sqlTool.abstractSQL.abstractSQL;

/**
 * 封装sql的代理类
 * 
 * @author 123
 * 
 */
public class SQLStringUtil {

	private String placeholder;
	private List<String> tableName = null;
	private List<String> filesName = null;
	private List<String> values = null;
	private List<String> where = null;
	private List<String> whereValues = null;
	private List<String> setValues = null;

	public SQLStringUtil() {
		setPlaceholder("%s");
	}

	/**
	 * 获取SQLstr的代理函数,传入相应的sql对象后调用对象的重写的getSQL函数
	 * 
	 * @param obj
	 *            相应增删查改sql对象
	 * @param getFullSQL
	 *            是否获取完整sql(不带占位符)
	 * 
	 * @return 当前对象
	 */
	public String getSQL(abstractSQL obj, Boolean getFullSQL) {
		String SQLStr = null;
		if (this.values != null) {
			SQLStr = obj.getSQL(this.placeholder, this.tableName,
					this.filesName, this.values, this.whereValues,
					this.setValues, getFullSQL);
			this.placeholder = "%s";
			this.tableName = null;
			this.filesName = null;
			this.values = null;
			this.where = null;
			this.whereValues = null;
			this.setValues = null;
			return SQLStr;
		}
		SQLStr = obj.getSQL(this.placeholder, this.tableName, this.filesName,
				this.where, this.whereValues, this.setValues, getFullSQL);
		this.placeholder = "%s";
		this.tableName = null;
		this.filesName = null;
		this.values = null;
		this.where = null;
		this.whereValues = null;
		this.setValues = null;
		return SQLStr;
	}

	/**
	 * 设置SQL的占位符默认是%s
	 * 
	 * @param placeholder
	 * @return
	 */
	public SQLStringUtil setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}

	/**
	 * 设置需要增删改查的表名
	 * 
	 * @param tableName
	 * @return
	 */
	public SQLStringUtil setSQLTableName(String... tableName) {
		this.tableName = Arrays.asList(tableName);
		return this;
	}

	/**
	 * 设置需要增删改查的字段名
	 * 
	 * @param FilesName
	 * @return
	 */
	public SQLStringUtil setSQLFileName(String... filesName) {
		this.filesName = Arrays.asList(filesName);
		return this;
	}

	/**
	 * 设置增删改查的where条件字段名
	 * 
	 * @param where
	 * @return
	 */
	public SQLStringUtil setSQLWhere(String... where) {
		this.where = Arrays.asList(where);
		return this;
	}

	/**
	 * 设置where条件
	 * 
	 * @param whereValue
	 * @return
	 */
	public SQLStringUtil setSQLWhereValues(String... whereValues) {
		if (this.where.size() == whereValues.length) {
			this.whereValues = Arrays.asList(whereValues);
		} else {
			throw new RuntimeException("where size NEQ whereValues length");
		}
		return this;
	}

	/**
	 * 设置update set value
	 * 
	 * @param setValues
	 * @return
	 */
	public SQLStringUtil setSQLSetValues(String... setValues) {
		if (this.filesName.size() == setValues.length) {
			this.setValues = Arrays.asList(setValues);
		} else {
			throw new RuntimeException("filesName size NEQ setValues length");
		}
		return this;
	}

	/**
	 * 设置insert into语句的values
	 * 
	 * @param value
	 * @return
	 */
	public SQLStringUtil setSQLValue(String... values) {
		this.values = Arrays.asList(values);
		return this;
	}

}